package com.example.e_commerce.ui.features.products.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.usecase.GetSpecificProductUseCases
import com.example.e_commerce.ui.utils.IoDispatcher
import com.example.e_commerce.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getSpecificProductUseCases: GetSpecificProductUseCases,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) :
    ViewModel(), ProductsDetailsContract.ViewModel {
    private var _states =
        MutableStateFlow<ProductsDetailsContract.State>(ProductsDetailsContract.State.Loading("loading"))
    override val state = _states
    private var _event = SingleLiveEvent<ProductsDetailsContract.Event>()
    override val event = _event

    override fun handleAction(action: ProductsDetailsContract.Action) {
        when (action) {
            is ProductsDetailsContract.Action.AddToCart -> {

            }

            is ProductsDetailsContract.Action.AddToWishlist -> {

            }

            is ProductsDetailsContract.Action.ClickOnCartIcon -> {
                navigateToCartActivity()

            }

            is ProductsDetailsContract.Action.LoadingProduct -> {
                loadingProductDetails(action.productId)
            }

            else -> {}
        }
    }

    private fun navigateToCartActivity() {

        _event.postValue(ProductsDetailsContract.Event.NavigateToCart)
    }

    private fun loadingProductDetails(productId: String) {
        viewModelScope.launch(ioDispatcher) {


            getSpecificProductUseCases.invoke(productId)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _states.emit(
                                ProductsDetailsContract.State.Error(
                                    response.error.localizedMessage
                                )
                            )

                        }


                        is ResultWrapper.ServerError -> _states.emit(
                            ProductsDetailsContract.State.Error(
                                response.error.serverMessage
                            )
                        )

                        is ResultWrapper.Success -> _states.emit(
                            ProductsDetailsContract.State.Success(
                                response.data
                            )
                        )

                        is ResultWrapper.Loading -> _states.emit(
                            ProductsDetailsContract.State.Loading(
                                "Loading"
                            )
                        )

                        else -> {}
                    }
                }

        }
    }
}