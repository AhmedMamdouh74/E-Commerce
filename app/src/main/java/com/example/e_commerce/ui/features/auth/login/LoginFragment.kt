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
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.LoginResponse
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentLoginBinding
import com.example.e_commerce.ui.features.auth.TokenViewModel
import com.example.e_commerce.ui.features.auth.register.RegisterFragment
import com.example.e_commerce.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.states.observe(viewLifecycleOwner, ::renderStates)
        viewModel.events.observe(viewLifecycleOwner, ::handelEvents)
        binding.loginBtn.setOnClickListener {
            viewModel.handleAction(LoginContract.Action.Login(viewModel.getRequest()))
        }
    }

    private fun handelEvents(event: LoginContract.Event?) {
        Log.d("TAG", "handelEvents:$event ")

        when (event) {
            is LoginContract.Event.NavigateToHomeScreen -> navigateToHomeScreen()
            else -> {}
        }
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
    }


    private fun renderStates(state: LoginContract.State?) {
        Log.d("TAG", "renderStates: $state")
        when (state) {
            is LoginContract.State.Error -> showError(state.message)
            is LoginContract.State.Loading -> showLoading(state.message)
            is LoginContract.State.Success -> login(state.loginResponse)
            else -> TODO()
        }


    }

    private fun showError(message: String) {
        binding.errorView.isVisible = true
        binding.loadingView.isVisible = false
        binding.successView.isVisible = false
        binding.errorText.text = message
        binding.btnTryAgain.setOnClickListener {
            binding.errorView.isVisible = false
            binding.loadingView.isVisible=false
            binding.successView.isVisible = true
        }

    }

    private fun showLoading(message: String) {
        binding.errorView.isVisible = false
        binding.loadingView.isVisible = true
        binding.successView.isVisible = false
        binding.errorText.text = message
    }

    private fun login(loginResponse: LoginResponse?) {
        binding.successView.isVisible=false
        binding.errorView.isVisible=false
        binding.loadingView.isVisible=false
        loginResponse?.user?.let {
            tokenViewModel.saveToken(
                loginResponse?.token ?: "", it
            )
        }

    }


    private fun initViews() {
        binding.lifecycleOwner = this
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