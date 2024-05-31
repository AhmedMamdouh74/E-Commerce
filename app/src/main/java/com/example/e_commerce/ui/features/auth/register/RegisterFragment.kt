package com.example.e_commerce.ui.features.auth.register

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
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse
import com.example.e_commerce.databinding.FragmentRegisterBinding
import com.example.e_commerce.ui.common.customviews.ProgressDialog
import com.example.e_commerce.ui.features.auth.UserViewModel
import com.example.e_commerce.ui.home.showRetrySnakeBarError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private var viewBinding: FragmentRegisterBinding? = null
    private val progressDialog by lazy { ProgressDialog.createProgressDialog(requireActivity()) }
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect {
                    renderStates(it)
                }
            }
        }

        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        binding.btnSignUp.setOnClickListener {
            viewModel.handleAction(RegisterContract.Action.Register(viewModel.getRegisterRequest()))


        }

    }

    private fun renderStates(state: RegisterContract.State?) {

        when (state) {
            is RegisterContract.State.Error -> showError(state.message)
            is RegisterContract.State.Loading -> showLoading(state.message)
            is RegisterContract.State.Success -> register(state.registerResponse)


            else -> {}
        }

    }

    private fun register(registerResponse: RegisterResponse?) {
        progressDialog.dismiss()
        registerResponse?.user?.let {userViewModel.saveToken(registerResponse.token ?: "", it) }


    }

    private fun showLoading(message: String) {
        progressDialog.show()
    }

    private fun showError(message: String) {
        progressDialog.dismiss()
        view?.showRetrySnakeBarError(message){
            viewModel.handleAction(RegisterContract.Action.Register(viewModel.getRegisterRequest()))
        }
    }




    private fun handleEvents(event: RegisterContract.Event?) {
        when (event) {
            is RegisterContract.Event.NavigateAuthenticatedRegisterToLogin -> navigateAuthenticatedRegisterToLogin(
                event.registerRequest
            )

            else -> {}
        }
    }

    private fun navigateAuthenticatedRegisterToLogin(registerRequest: RegisterRequest) {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    companion object {
        const val TAG = "RegisterFragment"
    }


}