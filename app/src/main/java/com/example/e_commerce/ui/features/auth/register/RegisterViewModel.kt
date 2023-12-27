package com.example.e_commerce.ui.features.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.RegisterRequest
import com.example.domain.usecase.GetRegisterUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getRegisterUseCases: GetRegisterUseCases,

    ) :
    ViewModel(), RegisterContract.ViewModel {
    private val _states = MutableLiveData<RegisterContract.State>()
    override val states: LiveData<RegisterContract.State>
        get() = _states

    private val _events = MutableLiveData<RegisterContract.Event>()
    override val events: LiveData<RegisterContract.Event>
        get() = _events
    var name = MutableLiveData<String>()
    var nameError = MutableLiveData<String?>()

    var mobile = MutableLiveData<String>()
    var mobileError = MutableLiveData<String?>()

    var email = MutableLiveData<String>()
    var emailError = MutableLiveData<String?>()

    var password = MutableLiveData<String>()
    var passwordError = MutableLiveData<String?>()

    var rePassword = MutableLiveData<String>()
    var rePasswordError = MutableLiveData<String?>()
//    var registerRequest: RegisterRequest = RegisterRequest(
//        name = name.value,
//        email = email.value,
//        password = password.value,
//        rePassword = rePassword.value,
//        phone = mobile.value
//
//    )

    fun getRegisterRequest(): RegisterRequest {
        return RegisterRequest(
            name = name.value,
            email = email.value,
            password = password.value,
            rePassword = rePassword.value,
            phone = mobile.value
        )
    }


    override fun handleAction(action: RegisterContract.Action) {
        when (action) {
            is RegisterContract.Action.Register -> validateAndRegister()

            else -> {}
        }
    }

    private fun validateAndRegister() {
        if (validateFields()) {
            val registerRequest = getRegisterRequest()
            register(registerRequest)
        }

    }


    private fun validateFields(): Boolean {
        var isValid = true
        if (name.value.isNullOrBlank()) {
            // show error
            nameError.postValue("Please enter name")
            isValid = false
        } else {
            nameError.postValue(null)
        }
        if (mobile.value.isNullOrBlank()) {
            // show error
            mobileError.postValue("Please enter mobile number")
            isValid = false
        } else {
            mobileError.postValue(null)
        }
        if (email.value.isNullOrBlank()) {
            // show error
            emailError.postValue("Please enter email")
            isValid = false
        } else {
            emailError.postValue(null)
        }
        if (password.value.isNullOrBlank()) {
            // show error
            passwordError.postValue("Please enter password")
            isValid = false
        } else {
            passwordError.postValue(null)
        }
        if (rePassword.value.isNullOrBlank() ||
            rePassword.value != password.value
        ) {
            // show error
            rePasswordError.postValue("password doesn't match")
            isValid = false
        } else {
            rePasswordError.postValue(null)
        }


        return isValid

    }

    private fun register(registerRequest: RegisterRequest) {

        _states.postValue(RegisterContract.State.Loading("Loading"))
        viewModelScope.launch {
            val response = getRegisterUseCases.invoke(registerRequest)
            Log.d("registerViewModelResponse ", "$response")
            when (response) {

                is ResultWrapper.Error -> _states.postValue(RegisterContract.State.Error(response.error.localizedMessage))
                is ResultWrapper.Loading -> TODO()
                is ResultWrapper.ServerError -> _states.postValue(
                    RegisterContract.State.Error(
                        response.error.serverMessage
                    )
                )

                is ResultWrapper.Success -> {
                    Log.d( "registerViewModelSuccess ","${response.data}")
                    _states.postValue(RegisterContract.State.Success(response.data))
                    navigateToLogin(registerRequest)
                }

                else -> {}
            }
        }

    }

    private fun navigateToLogin(registerRequest: RegisterRequest) {
        _events.postValue(RegisterContract.Event.NavigateAuthenticatedRegisterToLogin(registerRequest)
        )

    }
}