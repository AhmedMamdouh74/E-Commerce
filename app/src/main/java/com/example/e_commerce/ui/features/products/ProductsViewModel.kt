package com.example.e_commerce.ui.features.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.usecase.AddProductToWishlistUseCase
import com.example.domain.usecase.GetProductUseCases
import com.example.domain.usecase.RemoveProductFromWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val getProductUseCases: GetProductUseCases,
    private val addProductToWishlistUseCase: AddProductToWishlistUseCase,
    private val removeProductFromWishlistUseCase: RemoveProductFromWishlistUseCase) :
    ViewModel(), ProductContract.ViewModel {
    private var _states = MutableLiveData<ProductContract.State>()

    override val state = _states
    private val _wishlistState=MutableLiveData<ProductContract.WishlistState>()
    override val wishlistState: LiveData<ProductContract.WishlistState>
        get() =_wishlistState
    private var _events = MutableLiveData<ProductContract.Event>()

    override val event = _events

    override fun handleAction(action: ProductContract.Action) {
        when (action) {
            is ProductContract.Action.LoadingProducts -> loadingProducts(action.categoryId)
            is ProductContract.Action.ProductsClicked -> navigateToProductsDetails(action.product)
            is ProductContract.Action.AddProductToWishlist -> addProductToWishlist(action.productId,action.token)
            is ProductContract.Action.RemoveProductToWishlist -> removeProductFromWishlist(action.productId,action.token)
            is ProductContract.Action.CartIconClicked -> TODO()
        }
    }

    private fun removeProductFromWishlist(productId: String, token: String) {

        viewModelScope.launch {
            _wishlistState.postValue(ProductContract.WishlistState.Loading("Loading"))
            val response=removeProductFromWishlistUseCase.invoke(productId, token)
            _wishlistState.postValue(ProductContract.WishlistState.Success(productId,token ))

        }
    }

    private fun addProductToWishlist(productId:String,token:String) {
        viewModelScope.launch {
            _wishlistState.postValue(ProductContract.WishlistState.Loading("Loading"))
            val response=addProductToWishlistUseCase.invoke(productId,token)
            when(response){
                is ResultWrapper.Error -> _wishlistState.postValue(ProductContract.WishlistState.Error(response.error.localizedMessage))
                ResultWrapper.Loading -> {}
                is ResultWrapper.ServerError -> _wishlistState.postValue(ProductContract.WishlistState.Error(response.error.serverMessage))
                is ResultWrapper.Success -> _wishlistState.postValue(ProductContract.WishlistState.Success(productId,token))
            }


        }
    }

    private fun navigateToProductsDetails(product:Product) {
        _events.postValue(ProductContract.Event.NavigateToProductsDetails(product))
    }


    private fun loadingProducts(categoryId: String) {
        viewModelScope.launch {
            _states.postValue(ProductContract.State.Loading("Loading"))
            val response = getProductUseCases.invoke(categoryId)
            when (response) {
                is ResultWrapper.Success -> {
                    _states.postValue(
                        ProductContract.State.Success(
                            response.data ?: listOf()
                        )
                    )
                }
                is ResultWrapper.Error -> {
                    _states.postValue(
                        ProductContract.State.Error(response.error.localizedMessage)
                    )
                }


                is ResultWrapper.ServerError -> {
                    _states.postValue(
                        ProductContract.State.Error(
                            response.error.serverMessage
                        )
                    )
                }


                ResultWrapper.Loading -> TODO()
            }
        }

    }
}