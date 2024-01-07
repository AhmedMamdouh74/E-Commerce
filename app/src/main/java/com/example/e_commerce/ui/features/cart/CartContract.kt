package com.example.e_commerce.ui.features.cart

import androidx.lifecycle.LiveData
import com.example.domain.model.cart.loggedCart.CartQuantity

class CartContract {
    interface ViewModel {
        val event: LiveData<Event>
        val state: LiveData<State>
        fun handleAction(action: Action)
    }

    sealed class State {
        object Idle:State()
        class Loading(val message: String) : State()
        class Error(val message: String) : State()
        class Success(val cart: CartQuantity?) : State()
    }


    sealed class Action {
        class RemoveProductFromCart(
            val token: String,
            val productId: String
        ) : Action()

        object LoadingLoggedUserCarts : Action()
    }

    sealed class Event {}
}