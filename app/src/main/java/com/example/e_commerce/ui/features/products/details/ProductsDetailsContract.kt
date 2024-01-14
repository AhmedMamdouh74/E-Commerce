package com.example.e_commerce.ui.features.products.details

import androidx.lifecycle.LiveData
import com.example.domain.model.Product
import kotlinx.coroutines.flow.StateFlow

class ProductsDetailsContract {
    interface ViewModel {
        val state: StateFlow<State>
        val event: LiveData<Event>
        fun handleAction(action: Action)

    }

    sealed class Action {
        data class LoadingProduct(val productId: String) : Action()
        data class AddToCart(val productId: String) : Action()
        data class AddToWishlist(val productId: String) : Action()
        object ClickOnCartIcon: Action()


    }

    sealed class Event {
        object NavigateToCart : Event()
    }

    sealed class State {
        data class Success(val product: Product?) : State()
        data class Loading(val message: String) : State()
        data class Error(val message: String) : State()
    }
}