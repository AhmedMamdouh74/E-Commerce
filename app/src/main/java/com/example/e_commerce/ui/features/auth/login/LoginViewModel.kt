package com.example.e_commerce.ui.features.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.TokenManager
import com.example.domain.model.LoginRequest
import com.example.domain.usecase.GetLoginUseCase
import com.example.e_commerce.utils.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getAuthUseCases: GetLoginUseCase,
    val tokenManager: TokenManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), LoginContract.ViewModel {
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
            login()

        }
    }

    private fun login() {
        _states.value = LoginContract.State.Loading("loading")

        viewModelScope.launch(ioDispatcher) {

            try {
                val response = getAuthUseCases.invoke(getRequest())
                _states.value = LoginContract.State.Success(response)

                navigateToHome()
            } catch (ex: Exception) {
                _states.value = LoginContract.State.Error(ex.localizedMessage ?: "")
            }


        }

    }

    private fun navigateToHome() {
        _events.postValue(LoginContract.Event.NavigateToHomeScreen)
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
    private fun getRequest(): LoginRequest {
        return LoginRequest(
            email = email.value.trim().replace("\\s".toRegex(), ""),
            password = password.value.trim().replace("\\s".toRegex(), "")
        )
    }


}