package com.example.e_commerce.ui.features.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LoginRequest
import com.example.domain.usecase.GetLoginUseCases
import com.example.e_commerce.ui.utils.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginUseCases: GetLoginUseCases,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) :
    ViewModel(),
    LoginContract.ViewModel {
    private val _states =
        MutableStateFlow<LoginContract.State>(LoginContract.State.Idle)
    override val states = _states

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
            val loginRequest = getRequest()
            login(loginRequest)

        }
    }

    private fun login(loginRequest: LoginRequest) {
        _states.value=LoginContract.State.Loading("loading")

        viewModelScope.launch(ioDispatcher) {

            try {
                val response = getLoginUseCases.invoke(getRequest())
                _states.value = LoginContract.State.Success(response)

                navigateToHome(loginRequest)
            } catch (ex: Exception) {
                _states.value = LoginContract.State.Error(ex.localizedMessage?:"")
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

    var email = MutableStateFlow("")
    var emailError = MutableLiveData<String?>()
    var password = MutableStateFlow("")
    var passwordError = MutableLiveData<String?>()
    fun getRequest(): LoginRequest {
        return LoginRequest(
            email = email.value,
            password = password.value
        )
    }


}