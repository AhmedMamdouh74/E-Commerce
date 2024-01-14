package com.example.e_commerce.ui.home.wishlist

import androidx.lifecycle.LiveData
import com.example.domain.model.cart.Cart
import com.example.domain.model.Product
import com.example.domain.model.cart.loggedCart.CartQuantity
import kotlinx.coroutines.flow.StateFlow

class WishlistContract {
    interface ViewModel {
        val state: StateFlow<State>
        val cartState: StateFlow<CartState>
        val loggedUserCartState: StateFlow<LoggedUserCartState>
        val event: LiveData<Event>
        fun handleAction(action: Action)
    }

    sealed class State {
        object Idle : State()
        class Loading(val message: String) : State()
        class Error(val message: String) : State()
        class Success(val product: List<Product?>) : State()

    }

    sealed class CartState {
        class Loading(val message: String) : CartState()
        class Error(val message: String) : CartState()
        object Success : CartState()

    }

    sealed class LoggedUserCartState {
        class Loading(val message: String) : LoggedUserCartState()
        class Error(val message: String) : LoggedUserCartState()
        class Success(val cart: CartQuantity?) : LoggedUserCartState()
    }


    sealed class Event {
        object NavigateToCartScreen : Event()

    }

    sealed class Action {
        object LoadingFavouriteProducts : Action()
        class RemoveProductFromWishlist(val productId: String, val token: String) : Action()
        object CartClicked : Action()
        class AddProductToCart(val token: String, val productId: String) : Action()
    }
}