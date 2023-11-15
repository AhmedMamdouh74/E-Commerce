package com.example.e_commerce.ui.home.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Category
import com.example.domain.usecase.GetCategoriesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCases: GetCategoriesUseCases
) : ViewModel(){
//    override fun handleAction(action: CategoriesContract.Action) {
//        when (action) {
//            is CategoriesContract.Action.LoadingCategories -> {
//
//            }
//
//            is CategoriesContract.Action.CategoryClicked -> {
//
//            }
//        }
//
//    }

    val categoryLiveData = MutableLiveData<List<Category?>?>()
    fun getCategories() {
        viewModelScope.launch {
            val list = getCategoriesUseCases.invoke()
            categoryLiveData.postValue(list)
        }
    }

}