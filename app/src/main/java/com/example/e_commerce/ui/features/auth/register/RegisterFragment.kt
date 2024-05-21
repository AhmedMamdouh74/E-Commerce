package com.example.e_commerce.ui.features.auth.register

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
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.data.api.TokenManager
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentRegisterBinding
import com.example.e_commerce.ui.features.auth.TokenViewModel
import com.example.e_commerce.ui.features.auth.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var viewModel: RegisterViewModel
    private val tokenViewModel: TokenViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]


    }

    private var viewBinding: FragmentRegisterBinding? = null
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun renderStates(state: RegisterContract.State?) {
        Log.d("renderStates: ", "$state")
        when (state) {
            is RegisterContract.State.Error -> showError(state.message)
            is RegisterContract.State.Loading -> showLoading(state.message)
            is RegisterContract.State.Success -> register(state.registerResponse)


            else -> {}
        }

    }

    private fun register(registerResponse: RegisterResponse?) {
        Log.d("register33: ", "${registerResponse?.token}")
        binding.successView.isVisible = true
        binding.errorView.isVisible = false
        binding.loadingView.isVisible = false


        registerResponse?.user?.let { tokenViewModel.saveToken(registerResponse?.token ?: "", it) }
        Log.d("register: ", "${registerResponse?.token}")


    }

    private fun showLoading(message: String) {
        binding.successView.isVisible = false
        binding.errorView.isVisible = false
        binding.loadingView.isVisible = true
        binding.loadingText.text = message
    }

    private fun showError(message: String) {
        binding.successView.isVisible = false
        binding.errorView.isVisible = true
        binding.loadingView.isVisible = false
        binding.errorText.text = message
        binding.btnTryAgain.setOnClickListener {
            binding.successView.isVisible = true
            binding.errorView.isVisible = false


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect {
                    renderStates(it) }
            }
        }

        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        binding.btnSignUp.setOnClickListener {
            viewModel.handleAction(RegisterContract.Action.Register(viewModel.getRegisterRequest()))


        }

    }


    private fun handleEvents(event: RegisterContract.Event?) {
        when (event) {
            is RegisterContract.Event.NavigateAuthenticatedRegisterToLogin -> navigateAuthenticatedRegisterToLogin(
                event.registerRequest
            )

            null -> TODO()
        }
    }

    private fun navigateAuthenticatedRegisterToLogin(registerRequest: RegisterRequest) {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }


}