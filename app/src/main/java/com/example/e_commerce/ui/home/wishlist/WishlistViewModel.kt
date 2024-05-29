package com.example.e_commerce.ui.home.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.TokenManager
import com.example.domain.common.ResultWrapper
import com.example.domain.usecase.AddProductToCartUseCase
import com.example.domain.usecase.GetLoggedUserCartUseCases
import com.example.domain.usecase.GetLoggedUserWishlistUseCase
import com.example.domain.usecase.RemoveProductFromWishlistUseCase
import com.example.e_commerce.ui.utils.IoDispatcher
import com.example.e_commerce.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class WishlistViewModel @Inject constructor(
    private val removeProductFromWishlistUseCase: RemoveProductFromWishlistUseCase,
    private val getLoggedUserWishlistUseCase: GetLoggedUserWishlistUseCase,
    private val tokenManager: TokenManager,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getLoggedUserCartUseCases: GetLoggedUserCartUseCases,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher

) :
    ViewModel(), WishlistContract.ViewModel {
    private val _state =
        MutableStateFlow<WishlistContract.State>(WishlistContract.State.Loading("loading"))
    override val state = _state


    private val _event = SingleLiveEvent<WishlistContract.Event>()
    override val event: LiveData<WishlistContract.Event>
        get() = _event
    val token = tokenManager.getToken().toString()

    override fun handleAction(action: WishlistContract.Action) {
        when (action) {
            is WishlistContract.Action.CartClicked -> navigateToCart()
            is WishlistContract.Action.RemoveProductFromWishlist -> {
                removeProductFromWishlist(action.productId, action.token)
            }

            is WishlistContract.Action.LoadingFavouriteProducts -> {
                loadingFavouriteProducts()
                getLoggedUserCart(tokenManager.getToken().toString())
            }

            is WishlistContract.Action.AddProductToCart -> {
                addProductToCart(action.token, action.productId)
            }

            else -> {}
        }
    }

    private fun navigateToCart() {
        _event.postValue(WishlistContract.Event.NavigateToCartScreen)

    }

    fun getLoggedUserCart(token: String) {
        viewModelScope.launch(ioDispatcher) {
            getLoggedUserCartUseCases.invoke(token)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> _state.emit(
                            WishlistContract.State.Error(
                                response.error.localizedMessage
                            )
                        )

                        ResultWrapper.Loading ->
                            _state.emit(WishlistContract.State.Loading("loading"))


                        is ResultWrapper.ServerError -> _state.emit(
                            WishlistContract.State.Error(
                                response.error.serverMessage
                            )
                        )

                        is ResultWrapper.Success -> {
                            _state.emit(
                                WishlistContract.State.LoggedUserCartSuccess(
                                    response.data
                                )
                            )


                        }

                        else -> {}
                    }
                }

        }
    }

    private fun addProductToCart(token: String, productId: String) {

        viewModelScope.launch(ioDispatcher) {

            addProductToCartUseCase.invoke(token, productId)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error ->
                            _state.emit(
                                WishlistContract.State.Error(
                                    response.error
                                        .localizedMessage
                                )
                            )


                        ResultWrapper.Loading -> {
                            _state.emit(WishlistContract.State.Loading("Loading"))
                        }

                        is ResultWrapper.ServerError ->
                            _state.emit(
                                WishlistContract.State.Error(
                                    response.error
                                        .serverMessage
                                )
                            )


                        is ResultWrapper.Success -> {
                            _state.emit(WishlistContract.State.CartSuccess)


                        }

                        else -> {}
                    }
                }

        }
    }

    private fun removeProductFromWishlist(productId: String, token: String) {


        viewModelScope.launch(ioDispatcher) {
            removeProductFromWishlistUseCase.invoke(productId, token)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> _state.emit(WishlistContract.State.Error(response.error.localizedMessage))
                        ResultWrapper.Loading -> _state.emit(WishlistContract.State.Loading("loading"))
                        is ResultWrapper.ServerError -> _state.emit(
                            WishlistContract.State.Error(
                                response.error.serverMessage
                            )
                        )

                        is ResultWrapper.Success -> _state.emit(WishlistContract.State.CartSuccess)
                        else -> {}
                    }
                }


        }
    }

    private fun loadingFavouriteProducts() {
        viewModelScope.launch(ioDispatcher) {
            getLoggedUserWishlistUseCase.invoke(tokenManager.getToken().toString())
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> _state.emit(WishlistContract.State.Error(response.error.localizedMessage))
                        is ResultWrapper.Loading -> {
                            _state.emit(WishlistContract.State.Loading("loading"))
                        }

                        is ResultWrapper.ServerError -> _state.emit(
                            WishlistContract.State.Error(
                                response.error.serverMessage
                            )
                        )

                        is ResultWrapper.Success -> _state.emit(
                            WishlistContract.State.Success(
                                response.data ?: listOf()
                            )
                        )


                        else -> {}
                    }

                }


        }


    }
}