package com.example.e_commerce.ui.home.wishlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.TokenManager
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.usecase.AddProductToCartUseCase
import com.example.domain.usecase.GetLoggedUserCartUseCases
import com.example.domain.usecase.GetLoggedUserWishlistUseCase
import com.example.domain.usecase.RemoveProductFromWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class WishlistViewModel @Inject constructor(
    private val removeProductFromWishlistUseCase: RemoveProductFromWishlistUseCase,
    private val getLoggedUserWishlistUseCase: GetLoggedUserWishlistUseCase,
    private val tokenManager: TokenManager,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getLoggedUserCartUseCases: GetLoggedUserCartUseCases,

    ) :
    ViewModel(), WishlistContract.ViewModel {
    private val _state = MutableLiveData<WishlistContract.State>()
    override val state: LiveData<WishlistContract.State>
        get() = _state
    private val _cartState = MutableLiveData<WishlistContract.CartState>()
    override val cartState = _cartState
    private val _loggedUserCartState = MutableLiveData<WishlistContract.LoggedUserCartState>()
    override val loggedUserCartState = _loggedUserCartState

    private val _event = MutableLiveData<WishlistContract.Event>()
    override val event: LiveData<WishlistContract.Event>
        get() = _event

    override fun handleAction(action: WishlistContract.Action) {
        when (action) {
            is WishlistContract.Action.CartClicked -> {}
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

    fun getLoggedUserCart(token: String) {
        viewModelScope.launch {
            _loggedUserCartState.postValue(WishlistContract.LoggedUserCartState.Loading("loading"))
            val response = getLoggedUserCartUseCases.invoke(token)
            when (response) {
                is ResultWrapper.Error -> {}
                ResultWrapper.Loading -> {}
                is ResultWrapper.ServerError -> {}
                is ResultWrapper.Success -> {
                    _loggedUserCartState.postValue(
                        WishlistContract.LoggedUserCartState.Success(
                            response.data
                        )
                    )
                    Log.d("TAG", "getLoggedUserCartViewModel:${response.data} ")

                }

                else -> {}
            }
        }
    }

    private fun addProductToCart(token: String, productId: String) {

        viewModelScope.launch {
            _cartState.postValue(WishlistContract.CartState.Loading("Loading"))
            val response = addProductToCartUseCase.invoke(token, productId)
            when (response) {
                is ResultWrapper.Error -> {}
                ResultWrapper.Loading -> {}
                is ResultWrapper.ServerError -> {}
                is ResultWrapper.Success -> {
                    _cartState.postValue(WishlistContract.CartState.Success)
                    Log.d("TAG", "addProductToCartViewModel:${response} ")


                }

                else -> {}
            }
        }
    }

    private fun removeProductFromWishlist(productId: String, token: String) {

        _state.postValue(WishlistContract.State.Loading("loading"))
        viewModelScope.launch {
            removeProductFromWishlistUseCase.invoke(productId, token)
            _state.postValue(WishlistContract.State.Idle)

        }
    }

    private fun loadingFavouriteProducts() {
        _state.postValue(WishlistContract.State.Loading("loading"))
        viewModelScope.launch {
            val response = getLoggedUserWishlistUseCase.invoke(tokenManager.getToken().toString())
            Log.d("TAG", "loadingFavouriteProductsInvoke:$response ")
            when (response) {
                is ResultWrapper.Error -> _state.postValue(WishlistContract.State.Error(response.error.localizedMessage))
                is ResultWrapper.Loading -> {}
                is ResultWrapper.ServerError -> _state.postValue(
                    WishlistContract.State.Error(
                        response.error.serverMessage
                    )
                )

                is ResultWrapper.Success -> _state.postValue(
                    WishlistContract.State.Success(
                        response.data ?: listOf()
                    )
                )


                else -> {}
            }
            Log.d("TAG", "loadingFavouriteProducts:$response ")

        }


    }
}