package com.example.e_commerce.ui.home.categories


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
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

            }
        }

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

//    val categoryLiveData = MutableLiveData<List<Category?>?>()
//    fun getCategories() {
//        viewModelScope.launch {
//            val list = getCategoriesUseCases.invoke()
//            categoryLiveData.postValue(list)
//        }
//    }

}