package com.example.e_commerce.ui.features.products.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.usecase.GetSpecificProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val getSpecificProductUseCases: GetSpecificProductUseCases) :
    ViewModel(), ProductsDetailsContract.ViewModel {
    private var _states = MutableLiveData<ProductsDetailsContract.State>()
    override val state = _states
    private var _event = MutableLiveData<ProductsDetailsContract.Event>()
    override val event = _event

    override fun handleAction(action: ProductsDetailsContract.Action) {
        when (action) {
            is ProductsDetailsContract.Action.AddToCart -> {

            }
            is ProductsDetailsContract.Action.AddToWishlist -> {

            }
            is ProductsDetailsContract.Action.ClickOnCartIcon -> {

            }
            is ProductsDetailsContract.Action.LoadingProduct -> {
                loadingProductDetails(action.productId)
            }

            else -> {}
        }
    }

    private fun loadingProductDetails(productId: String) {
        viewModelScope.launch {
            _states.postValue(ProductsDetailsContract.State.Loading("Loading"))
            when (val response = getSpecificProductUseCases.invoke(productId)) {
                is ResultWrapper.Error -> {
                    ProductsDetailsContract.State.Error(
                        response.error.localizedMessage
                    )
                }


                is ResultWrapper.ServerError -> ProductsDetailsContract.State.Error(
                    response.error.serverMessage
                )

                is ResultWrapper.Success -> response.data

                is ResultWrapper.Loading -> TODO()
                else -> {}
            }
        }
    }
}