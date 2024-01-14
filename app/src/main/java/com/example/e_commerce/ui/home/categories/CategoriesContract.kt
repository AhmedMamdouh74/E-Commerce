package com.example.e_commerce.ui.home.categories

import androidx.lifecycle.LiveData
import com.example.domain.model.Category
import kotlinx.coroutines.flow.StateFlow

class CategoriesContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun handleAction(action: Action)


    }

    sealed class State {
        class Error(val message: String) : State()
        class Success(val category: List<Category?>) : State()
        class Loading(val message: String) : State()
    }

    sealed class Action {
        object LoadingCategories : Action()
        class CategoryClicked(val category: Category) : Action()
        object CartClicked : Action()
    }

    sealed class Event {
        class NavigateToSubCategories(val category: Category) : Event()
        object NavigateToCart : Event()
    }

}