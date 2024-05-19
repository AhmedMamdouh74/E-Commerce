package com.example.e_commerce.ui.home.categories


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.usecase.GetCategoriesUseCases
import com.example.e_commerce.ui.utils.IoDispatcher
import com.example.e_commerce.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCases: GetCategoriesUseCases,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), CategoriesContract.ViewModel {
    private val _states = MutableStateFlow<CategoriesContract.State>(CategoriesContract.State.Loading("loading"))
    override val states = _states
    private val _event = SingleLiveEvent<CategoriesContract.Event>()
    override val events = _event

    override fun handleAction(action: CategoriesContract.Action) {
        when (action) {
            is CategoriesContract.Action.LoadingCategories -> {
                loadCategories()

            }

            is CategoriesContract.Action.CategoryClicked -> {
                navigateToSubCategoryFragment(action.category)


            }

            CategoriesContract.Action.CartClicked -> navigateToCartActivity()
        }

    }

    private fun navigateToCartActivity() {
        _event.postValue(CategoriesContract.Event.NavigateToCart)
    }

    private fun navigateToSubCategoryFragment(category: Category) {
        _event.postValue(CategoriesContract.Event.NavigateToSubCategories(category))
    }

    private fun loadCategories() {
        viewModelScope.launch(ioDispatcher) {


            getCategoriesUseCases.invoke()
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Success -> {
                            _states.emit(
                                CategoriesContract.State.Success(
                                    response.data ?: listOf()
                                )
                            )
                        }

                        is ResultWrapper.ServerError -> {
                            _states.emit(CategoriesContract.State.Error(response.error.serverMessage))
                        }

                        is ResultWrapper.Error -> {
                            _states.emit(CategoriesContract.State.Error(response.error.localizedMessage))
                        }

                        is ResultWrapper.Loading -> _states.emit(
                            CategoriesContract.State.Loading(
                                "loading"
                            )
                        )

                        else -> {}
                    }
                }

        }
    }


}