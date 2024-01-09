package com.example.e_commerce.ui.home.categories


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.usecase.GetCategoriesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCases: GetCategoriesUseCases
) : ViewModel(), CategoriesContract.ViewModel {
    private val _states = MutableLiveData<CategoriesContract.State>()
    override val states = _states
    private val _event = MutableLiveData<CategoriesContract.Event>()
    override val events = _event

    override fun handleAction(action: CategoriesContract.Action) {
        when (action) {
            is CategoriesContract.Action.LoadingCategories -> {
                loadCategories()

            }

            is CategoriesContract.Action.CategoryClicked -> {
                showSubCategoryFragment(action.category)
                Log.d("categoryVm","${action.category}")

            }

            CategoriesContract.Action.CartClicked -> showCartFragment()
        }

    }

    private fun showCartFragment() {
        _event.postValue(CategoriesContract.Event.NavigateToCart)
    }

    private fun showSubCategoryFragment(category: Category?) {
        _event.postValue(CategoriesContract.Event.NavigateToSubCategories(category!!))
    }

    private fun loadCategories() {
        viewModelScope.launch {

            _states.postValue(CategoriesContract.State.Loading("Loading"))
            val response = getCategoriesUseCases.invoke()
            when (response) {
                is ResultWrapper.Success -> {
                    _states.postValue(CategoriesContract.State.Success(response.data ?: listOf()))
                }

                is ResultWrapper.ServerError -> {
                    _states.postValue(CategoriesContract.State.Error(response.error.serverMessage))
                }

                is ResultWrapper.Error -> {
                    _states.postValue(CategoriesContract.State.Error(response.error.localizedMessage))
                }

                is ResultWrapper.Loading -> {}
            }
        }
    }



}