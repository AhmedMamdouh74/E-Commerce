package com.example.e_commerce.ui.features.products

import androidx.lifecycle.LiveData
import com.example.domain.model.Product
import kotlinx.coroutines.flow.StateFlow

class ProductContract {
    interface ViewModel {
        val state: StateFlow<State>
        val event: LiveData<Event>
        fun handleAction(action: Action)

    }

    sealed class State {

        class Error(val message: String) : State()

        class Loading(val message: String) : State()
        class Success(val product: List<Product?>) : State()
        class SuccessLoggedWishlist(val wishlistProduct: List<Product?>) :State()
        object SuccessWishlist : State()
    }



    sealed class Action {
        class LoadingProducts(val categoryId: String) : Action()
        class ProductsClicked(val product: Product) : Action()
        class AddProductToWishlist(val productId: String, val token: String) : Action()
        class RemoveProductToWishlist(val productId: String, val token: String) : Action()
        class CartIconClicked(val product: Product) : Action()

    }

    sealed class Event {
        class NavigateToProductsDetails(val product: Product) : Event()
        class NavigateToCartScreen(val product: Product) : Event()

    }


}