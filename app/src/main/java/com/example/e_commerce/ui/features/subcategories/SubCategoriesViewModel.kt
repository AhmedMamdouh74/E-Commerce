package com.example.e_commerce.ui.features.subcategories


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.usecase.GetSubCategoriesUseCases
import com.example.e_commerce.ui.utils.IoDispatcher
import com.example.e_commerce.ui.utils.MainDispatcher
import com.example.e_commerce.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubCategoriesViewModel @Inject constructor(
    private val getSubCategoriesUseCases: GetSubCategoriesUseCases,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : ViewModel(), SubCategoriesContract.ViewModel {
    private val _states =
        MutableStateFlow<SubCategoriesContract.State>(SubCategoriesContract.State.Loading("loading"))
    override val states = _states
    private val _event = SingleLiveEvent<SubCategoriesContract.Event>()
    override val events = _event

    override fun handleAction(action: SubCategoriesContract.Action) {
        Log.d("TAG", "handleActionAhmed:$action ")
        when (action) {
            is SubCategoriesContract.Action.LoadingSubCategories -> {
                loadSubCategories(action.categoryId)

            }

            is SubCategoriesContract.Action.SubCategoriesClicked -> {
                navigateToSubCategoriesProducts(action.categoryId)

            }


        }

    }

    private fun navigateToSubCategoriesProducts(categoryId: String) {
        viewModelScope.launch(ioDispatcher) {
            _event.postValue(SubCategoriesContract.Event.NavigateToCategoriesProducts(categoryId))
        }

    }

    private fun loadSubCategories(categoryId: String) {
        viewModelScope.launch(ioDispatcher) {


            getSubCategoriesUseCases.invoke(categoryId)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Success -> {
                            _states.emit(
                                SubCategoriesContract.State.Success(
                                    response.data ?: listOf()
                                )
                            )
                        }

                        is ResultWrapper.ServerError -> {
                            _states.emit(SubCategoriesContract.State.Error(response.error.serverMessage))
                        }

                        is ResultWrapper.Error -> {
                            _states.emit(SubCategoriesContract.State.Error(response.error.localizedMessage))
                        }

                        is ResultWrapper.Loading -> _states.emit(
                            SubCategoriesContract.State.Loading(
                                "Loading"
                            )
                        )

                        else -> {}
                    }
                }

        }
    }




}