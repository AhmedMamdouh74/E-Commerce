package com.example.e_commerce.ui.features.auth.register

import android.webkit.ConsoleMessage
import androidx.lifecycle.LiveData
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse

class RegisterContract {
    interface ViewModel {
        val states: LiveData<State>
        val events: LiveData<Event>
        fun handleAction(action: Action)

    }

    sealed class State {
        class Loading( val message: String) : State()
        class Error(val message: String) : State()
        class Success(val registerResponse: RegisterResponse?) : State()
    }

    sealed class Action {
        class Register(val registerRequest: RegisterRequest) : Action()
    }

    sealed class Event {
        class NavigateAuthenticatedRegisterToLogin(val registerRequest: RegisterRequest) :
            Event()
    }

}