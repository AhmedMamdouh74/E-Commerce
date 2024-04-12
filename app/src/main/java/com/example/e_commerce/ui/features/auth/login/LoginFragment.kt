package com.example.e_commerce.ui.features.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.model.LoginResponse
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentLoginBinding
import com.example.e_commerce.ui.features.auth.TokenViewModel
import com.example.e_commerce.ui.features.auth.register.RegisterFragment
import com.example.e_commerce.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private val tokenViewModel: TokenViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]


    }

    private var viewBinding: FragmentLoginBinding? = null
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        lifecycleScope.launch{
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
        Log.d("TAG", "handelEventsLogin:$event ")
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
    }


    private fun renderStates(state: LoginContract.State?) {
        Log.d("TAG", "renderStatesLogin: $state")
        when (state) {
            is LoginContract.State.Error -> showError(state.message)
            is LoginContract.State.Loading -> showLoading(state.message)
            is LoginContract.State.Success -> login(state.loginResponse)
            LoginContract.State.Idle -> showIdle()
            null -> TODO()
        }


    }

    private fun showIdle() {

        binding.errorView.isVisible = false
        binding.loadingView.isVisible = false
        binding.successView.isVisible = true
    }

    private fun showError(message: String) {
        binding.errorView.isVisible = true
        binding.loadingView.isVisible = false
        binding.successView.isVisible = false
        binding.errorText.text = message
        binding.btnTryAgain.setOnClickListener {
            binding.errorView.isVisible = false
            binding.loadingView.isVisible = false
            binding.successView.isVisible = true
        }

    }

    private fun showLoading(message: String) {
        binding.errorView.isVisible = false
        binding.loadingView.isVisible = true
        binding.successView.isVisible = true
        binding.errorText.text = message
    }

    private fun login(loginResponse: LoginResponse?) {
        binding.successView.isVisible = true
        binding.errorView.isVisible = false
        binding.loadingView.isVisible = true

        loginResponse?.user?.let {
            tokenViewModel.saveToken(
                loginResponse?.token ?: "", it
            )
        }


    }


    private fun initViews() {

        binding.vm = viewModel
        binding.createAccount.setOnClickListener {
            createAccount()
        }

    }

    private fun createAccount() {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.register_login_container, RegisterFragment())
            .commit()

    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null

    }
}