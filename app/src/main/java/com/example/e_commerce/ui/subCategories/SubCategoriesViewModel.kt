package com.example.e_commerce.ui.subCategories



import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.usecase.GetSubCategoriesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubCategoriesViewModel @Inject constructor(
    private val getSubCategoriesUseCases: GetSubCategoriesUseCases
) : ViewModel(), SubCategoriesContract.ViewModel {
    private val _states = MutableLiveData<SubCategoriesContract.State>()
    override val states = _states
    private val _event = MutableLiveData<SubCategoriesContract.Event>()
    override val events = _event

    override fun handleAction(action: SubCategoriesContract.Action) {
        when (action) {
            is SubCategoriesContract.Action.LoadingSubCategories -> {
                loadSubCategories(action.categoryId)

            }

            is SubCategoriesContract.Action.SubCategoriesClicked -> {

            }

            else -> {}
        }

    }

    private fun loadSubCategories(categoryId: String) {
        viewModelScope.launch {

            _states.postValue(SubCategoriesContract.State.Loading("Loading"))
            val response = getSubCategoriesUseCases.invoke(categoryId)
            when (response) {
                is ResultWrapper.Success -> {
                    _states.postValue(
                        SubCategoriesContract.State.Success(
                            response.data?: listOf()
                        )
                    )
                }

                is ResultWrapper.ServerError -> {
                    _states.postValue(SubCategoriesContract.State.Error(response.error.serverMessage))
                }

                is ResultWrapper.Error -> {
                    _states.postValue(SubCategoriesContract.State.Error(response.error.localizedMessage))
                }

                is ResultWrapper.Loading -> {}
            }
        }
    }
    companion object{

    }



}