package com.example.e_commerce.ui.features.products

import androidx.lifecycle.LiveData
import com.example.domain.model.Category
import com.example.domain.model.Product

class ProductContract {
    interface ViewModel{
        val state:LiveData<State>
        val event:LiveData<Event>
        fun handleAction( action: Action)

    }
    sealed class State{
        class Error(val message:String):State()
        class Success(val product: List<Product?>):State()
        class Loading(val message: String):State()
    }
    sealed class Action{
        class LoadingProducts(val categoryId:String):Action()
        class ProductsClicked(val product:Product):Action()

    }
    sealed class Event{
        class NavigateToProductsDetails(val product: Product):Event()

    }

}