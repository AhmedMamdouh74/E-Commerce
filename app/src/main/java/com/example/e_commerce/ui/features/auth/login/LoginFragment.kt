package com.example.e_commerce.ui.features.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.domain.model.LoginResponse
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentLoginBinding
import com.example.e_commerce.ui.common.customviews.ProgressDialog
import com.example.e_commerce.ui.features.auth.UserViewModel
import com.example.e_commerce.ui.home.HomeActivity
import com.example.e_commerce.ui.home.showRetrySnakeBarError
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private var viewBinding: FragmentLoginBinding? = null
    private var snackbar:Snackbar?=null
    private val binding get() = viewBinding!!
    private val progressDialog by lazy { ProgressDialog.createProgressDialog(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect {
                    renderStates(it)
                }
            }
        }

        viewModel.events.observe(viewLifecycleOwner, ::handelEvents)
        binding.loginBtn.setOnClickListener {
            viewModel.handleAction(LoginContract.Action.Login(viewModel.getRequest()))
        }
    }

    private fun handelEvents(event: LoginContract.Event?) {


        when (event) {
            is LoginContract.Event.NavigateToHomeScreen -> navigateToHomeScreen()
            else -> {}

        }

    }

    private fun navigateToHomeScreen() {
        requireActivity().startActivity(Intent(activity, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        requireActivity().finish()

    }


    private fun renderStates(state: LoginContract.State?) {

        when (state) {
            is LoginContract.State.Error -> showError(state.message)
            is LoginContract.State.Loading -> showLoading(state.message)
            is LoginContract.State.Success -> login(state.loginResponse)
            LoginContract.State.Idle -> showIdle()
            else -> {}
        }


    }

    private fun showIdle() {
        progressDialog.dismiss()

    }

    private fun showError(message: String) {
        progressDialog.dismiss()
        snackbar?.dismiss()
        snackbar=view?.showRetrySnakeBarError(message) {
            viewModel.handleAction(LoginContract.Action.Login(viewModel.getRequest()))
        }

    }

    private fun showLoading(message: String) {
        progressDialog.show()
    }

    private fun login(loginResponse: LoginResponse?) {
        progressDialog.dismiss()
        loginResponse?.user?.let {
            userViewModel.saveToken(
                loginResponse.token ?: "", it
            )
        }


    }


    private fun initViews() {
        binding.createAccount.setOnClickListener {
            createAccount()
        }

    }

    private fun createAccount() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null


    }

    override fun onPause() {
        super.onPause()
        snackbar?.dismiss()
    }

    companion object {
        const val TAG = "LoginFragment"
    }
}
