package com.example.e_commerce.ui.features.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.RegisterRequest
import com.example.domain.usecase.GetRegisterUseCases
import com.example.e_commerce.ui.utils.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getRegisterUseCases: GetRegisterUseCases,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher

) :
    ViewModel(), RegisterContract.ViewModel {
    private val _states =
        MutableStateFlow<RegisterContract.State>(RegisterContract.State.Idle)
    override val states: StateFlow<RegisterContract.State>
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

        _states.value = RegisterContract.State.Loading("loading")
        viewModelScope.launch(ioDispatcher) {

            try {
                val response = getRegisterUseCases.invoke(registerRequest)
                _states.value = RegisterContract.State.Success(response)
                navigateToLogin(registerRequest)
            } catch (ex: Exception) {
                _states.value = RegisterContract.State.Error(ex.message ?: "")
            }


        }

    }

    private fun navigateToLogin(registerRequest: RegisterRequest) {
        _events.postValue(
            RegisterContract.Event.NavigateAuthenticatedRegisterToLogin(registerRequest)
        )

    }
}