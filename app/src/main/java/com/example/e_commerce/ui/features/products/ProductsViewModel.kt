package com.example.e_commerce.ui.features.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.TokenManager
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.usecase.AddProductToWishlistUseCase
import com.example.domain.usecase.GetLoggedUserWishlistUseCase
import com.example.domain.usecase.GetProductUseCases
import com.example.domain.usecase.RemoveProductFromWishlistUseCase
import com.example.e_commerce.ui.utils.IoDispatcher
import com.example.e_commerce.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductUseCases: GetProductUseCases,
    private val addProductToWishlistUseCase: AddProductToWishlistUseCase,
    private val removeProductFromWishlistUseCase: RemoveProductFromWishlistUseCase,
    private val getLoggedUserWishlistUseCase: GetLoggedUserWishlistUseCase,
    private val tokenManager: TokenManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher

) :
    ViewModel(), ProductContract.ViewModel {
    private var _states =
        MutableStateFlow<ProductContract.State>(ProductContract.State.Loading("loading"))
    override val state = _states


    private var _events = SingleLiveEvent<ProductContract.Event>()

    override val event = _events
    val token = tokenManager.getToken().toString()

    override fun handleAction(action: ProductContract.Action) {
        when (action) {
            is ProductContract.Action.LoadingProducts -> {
                loadingProducts(action.categoryId)
                getLoggedWishlist()

            }

            is ProductContract.Action.ProductsClicked -> navigateToProductsDetails(action.product)
            is ProductContract.Action.AddProductToWishlist -> addProductToWishlist(
                action.productId,
                action.token
            )

            is ProductContract.Action.RemoveProductToWishlist -> removeProductFromWishlist(
                action.productId,
                action.token
            )

            is ProductContract.Action.CartIconClicked -> {}
            else -> {}
        }
    }

    private fun removeProductFromWishlist(productId: String, token: String) {

        viewModelScope.launch(ioDispatcher) {

            removeProductFromWishlistUseCase.invoke(productId, token)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> _states.emit(
                            ProductContract.State.Error(
                                response.error.localizedMessage
                            )
                        )

                        ResultWrapper.Loading -> _states.emit(
                            ProductContract.State.Loading(
                                "Loading"
                            )
                        )

                        is ResultWrapper.ServerError -> _states.emit(
                            ProductContract.State.Error(
                                response.error.serverMessage
                            )
                        )

                        is ResultWrapper.Success -> _states.emit(ProductContract.State.SuccessWishlist)
                        else -> {}
                    }
                }


        }
    }

    private fun addProductToWishlist(productId: String, token: String) {
        viewModelScope.launch(ioDispatcher) {

            addProductToWishlistUseCase.invoke(token, productId)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> _states.emit(
                            ProductContract.State.Error(
                                response.error.localizedMessage
                            )
                        )

                        ResultWrapper.Loading -> _states.emit(
                            ProductContract.State.Loading(
                                "Loading"
                            )
                        )

                        is ResultWrapper.ServerError -> _states.emit(
                            ProductContract.State.Error(
                                response.error.serverMessage
                            )
                        )

                        is ResultWrapper.Success -> _states.emit(ProductContract.State.SuccessWishlist)
                        else -> {}
                    }
                }


        }
    }

    private fun navigateToProductsDetails(product: Product) {
        _events.postValue(ProductContract.Event.NavigateToProductsDetails(product))
    }


    private fun loadingProducts(categoryId: String) {
        handleProduct(State.PRODUCT) { getProductUseCases.invoke(categoryId) }
    }

    fun getLoggedWishlist() {
        handleProduct(State.LOGGED_WISHLIST) {
            getLoggedUserWishlistUseCase.invoke(
                tokenManager.getToken().toString()
            )
        }
    }

    private fun handleProduct(
        state: State,
        wishlist: suspend () -> Flow<ResultWrapper<List<Product?>?>>
    ) {
        viewModelScope.launch(ioDispatcher) {
            wishlist().collect {
                when (it) {
                    is ResultWrapper.Error -> _states.emit(
                        ProductContract.State.Error(
                            it.error.localizedMessage
                        )
                    )

                    ResultWrapper.Loading -> _states.emit(
                        ProductContract.State.Loading(
                            "loading"
                        )
                    )

                    is ResultWrapper.ServerError -> _states.emit(
                        ProductContract.State.Error(
                            it.error.serverMessage
                        )
                    )

                    is ResultWrapper.Success -> {
                        when (state) {
                            State.PRODUCT -> _states.emit(
                                ProductContract.State.Success(
                                    it.data ?: listOf()
                                )
                            )

                            State.WISHLIST -> _states.emit(ProductContract.State.SuccessWishlist)
                            State.LOGGED_WISHLIST -> _states.emit(
                                ProductContract.State.SuccessLoggedWishlist(
                                    it.data ?: listOf()
                                )
                            )
                        }
                    }
                }
            }

        }

    }

}