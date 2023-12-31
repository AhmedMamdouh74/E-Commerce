package com.example.e_commerce.ui.home.wishlist

import androidx.lifecycle.LiveData
import com.example.domain.model.Product

class WishlistContract {
    interface ViewModel {
        val state: LiveData<State>
        val event: LiveData<Event>
        fun handleAction(action: Action)
    }

    sealed class State {
        class Idle(productId: String,token: String) : State()
        class Loading(val message: String) : State()
        class Error(val message: String) : State()
        class Success(val product: List<Product?>) : State()

    }

    sealed class Event {
        object NavigateToCartScreen : Event()

    }

    sealed class Action {
        object LoadingFavouriteProducts : Action()
        class RemoveProductFromWishlist(val productId: String, val token: String) : Action()
        object CartClicked : Action()
    }
}