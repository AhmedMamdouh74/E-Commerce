package com.example.e_commerce.ui.features.auth.login

import androidx.lifecycle.LiveData
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse

class LoginContract {
    interface ViewModel {
        val states: LiveData<State>
        val events: LiveData<Event>
        fun handleAction(action: Action)
    }

    sealed class State {
        class Loading(val message: String) : State()
        class Error(val message: String) : State()
        class Success(val loginResponse: LoginResponse?) : State()
    }

    sealed class Event {
        class NavigateToHomeScreen(val loginRequest: LoginRequest) : Event()
    }

    sealed class Action {
        class Login(val loginRequest: LoginRequest) : Action()
    }
}