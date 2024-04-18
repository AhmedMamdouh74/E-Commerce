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
import com.example.e_commerce.ui.home.wishlist.WishlistContract
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
    private val _wishlistState =
        MutableStateFlow<ProductContract.WishlistState>(
            ProductContract.WishlistState.Loading(
                "loading"
            )
        )
    override val wishlistState = _wishlistState
    private var _loggedWishlistState = MutableStateFlow<ProductContract.LoggedWishlistState>(
        ProductContract.LoggedWishlistState.Loading("loading")
    )
    override val loggedWishlistState = _loggedWishlistState

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
                        is ResultWrapper.Error -> _wishlistState.emit(
                            ProductContract.WishlistState.Error(
                                response.error.localizedMessage
                            )
                        )

                        ResultWrapper.Loading -> _wishlistState.emit(
                            ProductContract.WishlistState.Loading(
                                "Loading"
                            )
                        )

                        is ResultWrapper.ServerError -> _wishlistState.emit(
                            ProductContract.WishlistState.Error(
                                response.error.serverMessage
                            )
                        )

                        is ResultWrapper.Success -> _wishlistState.emit(ProductContract.WishlistState.Success)
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
                        is ResultWrapper.Error -> _wishlistState.emit(
                            ProductContract.WishlistState.Error(
                                response.error.localizedMessage
                            )
                        )

                        ResultWrapper.Loading -> _wishlistState.emit(
                            ProductContract.WishlistState.Loading(
                                "Loading"
                            )
                        )

                        is ResultWrapper.ServerError -> _wishlistState.emit(
                            ProductContract.WishlistState.Error(
                                response.error.serverMessage
                            )
                        )

                        is ResultWrapper.Success -> _wishlistState.emit(
                            ProductContract.WishlistState.Success
                        )
                    }
                }


        }
    }

    private fun navigateToProductsDetails(product: Product) {
        _events.postValue(ProductContract.Event.NavigateToProductsDetails(product))
    }


    private fun loadingProducts(categoryId: String) {
        viewModelScope.launch(ioDispatcher) {
            _states.emit(ProductContract.State.Loading("Loading"))
            getProductUseCases.invoke(categoryId)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Success -> {
                            _states.emit(
                                ProductContract.State.Success(
                                    response.data ?: listOf()
                                )
                            )
                        }

                        is ResultWrapper.Error -> {
                            _states.emit(
                                ProductContract.State.Error(response.error.localizedMessage)
                            )
                        }


                        is ResultWrapper.ServerError -> {
                            _states.emit(
                                ProductContract.State.Error(
                                    response.error.serverMessage
                                )
                            )
                        }


                        ResultWrapper.Loading -> {}

                        else -> {}
                    }
                }
        }

    }

    fun getLoggedWishlist() {
        viewModelScope.launch(ioDispatcher) {

            getLoggedUserWishlistUseCase.invoke(tokenManager.getToken().toString())
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> _loggedWishlistState.emit(
                            ProductContract.LoggedWishlistState.Error(
                                response.error.localizedMessage
                            )
                        )

                        ResultWrapper.Loading -> _loggedWishlistState.emit(
                            ProductContract.LoggedWishlistState.Loading(
                                "loading"
                            )
                        )

                        is ResultWrapper.ServerError -> _loggedWishlistState.emit(
                            ProductContract.LoggedWishlistState.Error(
                                response.error.serverMessage
                            )
                        )

                        is ResultWrapper.Success -> _loggedWishlistState.emit(
                            ProductContract.LoggedWishlistState.Success(
                                response.data ?: listOf()
                            )

                        )

                        else -> {}
                    }
                }

        }
    }

//    private fun handleWishlist(wishlist: suspend () -> Flow<ResultWrapper<List<Product?>>>) {
//        viewModelScope.launch(ioDispatcher) {
//            wishlist().collect {
//                when (it) {
//                    is ResultWrapper.Error -> _wishlistState.emit(
//                        ProductContract.WishlistState.Error(
//                            it.error.localizedMessage
//                        )
//                    )
//
//                    ResultWrapper.Loading -> _wishlistState.emit(
//                        ProductContract.WishlistState.Loading(
//                            "loading"
//                        )
//                    )
//
//                    is ResultWrapper.ServerError -> _wishlistState.emit(
//                        ProductContract.WishlistState.Error(
//                            it.error.serverMessage
//                        )
//                    )
//
//                    is ResultWrapper.Success -> _wishlistState.emit(ProductContract.WishlistState.Success)
//                }
//            }
//
//        }
//
//    }
}