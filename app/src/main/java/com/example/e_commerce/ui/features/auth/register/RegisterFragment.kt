package com.example.e_commerce.ui.features.auth.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.data.api.TokenManager
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentRegisterBinding
import com.example.e_commerce.ui.features.auth.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var viewModel: RegisterViewModel
    val tokenViewModel: TokenViewModel by viewModels()


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
        binding.successView.isVisible = true
        binding.errorView.isVisible = true
        binding.loadingView.isVisible = false
        binding.errorText.text = message
        binding.btnTryAgain.setOnClickListener {

            viewModel.handleAction(RegisterContract.Action.Register(viewModel.getRegisterRequest()))

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        viewModel.states.observe(viewLifecycleOwner, ::renderStates)
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
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }


}