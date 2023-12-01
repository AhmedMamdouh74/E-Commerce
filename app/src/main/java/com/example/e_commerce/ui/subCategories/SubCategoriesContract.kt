package com.example.e_commerce.ui.subCategories

import androidx.lifecycle.LiveData
import com.example.domain.model.Category
import com.example.domain.model.SubCategories

class SubCategoriesContract {
    interface ViewModel {
        val states: LiveData<State>
        val events: LiveData<Event>
        fun handleAction(action: Action)


    }

    sealed class State {
        class Error(val message: String) : State()
        class Success(val subcategory: List<SubCategories?>) : State()
        class Loading(val message: String) : State()
    }

    sealed class Action {
        class LoadingSubCategories(val categoryId: String) : Action()
        class SubCategoriesClicked(val categoryId: String): Action()
    }

    sealed class Event {
        class NavigateToCategoriesProducts(val categoryId: String) : Event()
    }

}