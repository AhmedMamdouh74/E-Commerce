package com.example.e_commerce.ui.features.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.TokenManager
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.usecase.AddProductToWishlistUseCase
import com.example.domain.usecase.GetLoggedUserWishlistUseCase
import com.example.domain.usecase.GetProductUseCases
import com.example.domain.usecase.RemoveProductFromWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductUseCases: GetProductUseCases,
    private val addProductToWishlistUseCase: AddProductToWishlistUseCase,
    private val removeProductFromWishlistUseCase: RemoveProductFromWishlistUseCase,
    private val getLoggedUserWishlistUseCase: GetLoggedUserWishlistUseCase,
    private val tokenManager: TokenManager
) :
    ViewModel(), ProductContract.ViewModel {
    private var _states = MutableLiveData<ProductContract.State>()


    override val state = _states
    private val _wishlistState = MutableLiveData<ProductContract.WishlistState>()
    override val wishlistState: LiveData<ProductContract.WishlistState>
        get() = _wishlistState
    private var _loggedWishlistState = MutableLiveData<ProductContract.LoggedWishlistState>()
    override val loggedWishlistState: LiveData<ProductContract.LoggedWishlistState>
        get() = _loggedWishlistState

    private var _events = MutableLiveData<ProductContract.Event>()

    override val event = _events

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

        viewModelScope.launch {
            _wishlistState.postValue(ProductContract.WishlistState.Loading("Loading"))
             removeProductFromWishlistUseCase.invoke(productId, token)
            _wishlistState.postValue(ProductContract.WishlistState.Success(productId, token))

        }
    }

    private fun addProductToWishlist(productId: String, token: String) {
        viewModelScope.launch {
            _wishlistState.postValue(ProductContract.WishlistState.Loading("Loading"))
            val response = addProductToWishlistUseCase.invoke(token, productId)
            _wishlistState.postValue(
                ProductContract.WishlistState.Success(
                    productId,
                    token
                )
            )
//            when (response) {
//                is ResultWrapper.Error -> _wishlistState.postValue(
//                    ProductContract.WishlistState.Error(
//                        response.error.localizedMessage
//                    )
//                )
//
//                ResultWrapper.Loading -> {}
//                is ResultWrapper.ServerError -> _wishlistState.postValue(
//                    ProductContract.WishlistState.Error(
//                        response.error.serverMessage
//                    )
//                )
//
//                is ResultWrapper.Success ->{}
//
//                else -> {}
//            }


        }
    }

    private fun navigateToProductsDetails(product: Product) {
        _events.postValue(ProductContract.Event.NavigateToProductsDetails(product))
    }


    private fun loadingProducts(categoryId: String) {
        viewModelScope.launch {
            _states.postValue(ProductContract.State.Loading("Loading"))
            val getProductResponse = getProductUseCases.invoke(categoryId)

            when (getProductResponse) {
                is ResultWrapper.Success -> {
                    _states.postValue(
                        ProductContract.State.Success(
                            getProductResponse.data ?: listOf()
                        )
                    )
                }

                is ResultWrapper.Error -> {
                    _states.postValue(
                        ProductContract.State.Error(getProductResponse.error.localizedMessage)
                    )
                }


                is ResultWrapper.ServerError -> {
                    _states.postValue(
                        ProductContract.State.Error(
                            getProductResponse.error.serverMessage
                        )
                    )
                }


                ResultWrapper.Loading -> {}
            }

        }

    }

    fun getLoggedWishlist() {
        viewModelScope.launch {
            val getWishlistResponse =
                getLoggedUserWishlistUseCase.invoke(tokenManager.getToken().toString())
            when (getWishlistResponse) {
                is ResultWrapper.Error -> {}
                ResultWrapper.Loading -> {}
                is ResultWrapper.ServerError -> {}
                is ResultWrapper.Success -> _loggedWishlistState.postValue(
                    ProductContract.LoggedWishlistState.Success(
                        getWishlistResponse.data ?: listOf()
                    )

                )
            }
        }
    }
}