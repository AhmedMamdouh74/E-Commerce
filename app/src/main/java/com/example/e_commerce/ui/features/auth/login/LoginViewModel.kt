package com.example.e_commerce.ui.features.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.usecase.GetLoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val getLoginUseCases: GetLoginUseCases) :
    ViewModel(),
    LoginContract.ViewModel {
    private val _states = MutableLiveData<LoginContract.State>()
    override val states: MutableLiveData<LoginContract.State>
        get() = _states

    private val _events = MutableLiveData<LoginContract.Event>()
    override val events: LiveData<LoginContract.Event>
        get() = _events

    override fun handleAction(action: LoginContract.Action) {
        when (action) {
            is LoginContract.Action.Login -> validateAndLogin()


        }
    }

    private fun validateAndLogin() {
        if (validFields()) {
            val loginRequest=getRequest()
            login(loginRequest)

        }
    }

    private fun login(loginRequest: LoginRequest) {

        _states.postValue(LoginContract.State.Loading("loading"))
        viewModelScope.launch {
            val response = getLoginUseCases.invoke(getRequest())
            when (response) {
                is ResultWrapper.Error -> _states.postValue(LoginContract.State.Error(response.error.localizedMessage))
               is ResultWrapper.Loading -> {}
                is ResultWrapper.ServerError -> _states.postValue(LoginContract.State.Error(response.error.serverMessage))
                is ResultWrapper.Success -> {_states.postValue(LoginContract.State.Success(response.data))
                    Log.d("TAG", "login: ${response.data}")
                    navigateToHome(loginRequest)
                }
            }
        }

    }

    private fun navigateToHome(loginRequest: LoginRequest) {
        _events.postValue(LoginContract.Event.NavigateToHomeScreen(loginRequest))
    }

    private fun validFields(): Boolean {
        var isValid = true
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
        return isValid
    }

    var email = MutableLiveData("")
    var emailError = MutableLiveData<String?>()
    var password = MutableLiveData("")
    var passwordError = MutableLiveData<String?>()
    fun getRequest(): LoginRequest {
        return LoginRequest(
            email = email.value,
            password = password.value
        )
    }


}