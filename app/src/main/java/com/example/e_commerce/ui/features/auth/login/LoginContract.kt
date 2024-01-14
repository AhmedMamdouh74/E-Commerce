package com.example.e_commerce.ui.features.auth.login

import androidx.lifecycle.LiveData
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import kotlinx.coroutines.flow.StateFlow

class LoginContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun handleAction(action: Action)
    }

    sealed class State {
        object Idle:State()
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