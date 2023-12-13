package com.example.e_commerce.ui.features.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.usecase.GetProductUseCases
import dagger.Module
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val getProductUseCases: GetProductUseCases) :
    ViewModel(), ProductContract.ViewModel {
    private var _states = MutableLiveData<ProductContract.State>()

    override val state = _states
    private var _events = MutableLiveData<ProductContract.Event>()

    override val event = _events

    override fun handleAction(action: ProductContract.Action) {
        when (action) {
            is ProductContract.Action.LoadingProducts -> loadingProducts(action.categoryId)
            is ProductContract.Action.ProductsClicked -> navigateToProductsDetails(action.product)
        }
    }

    private fun navigateToProductsDetails(product:Product) {
        _events.postValue(ProductContract.Event.NavigateToProductsDetails(product))
    }
//    suspend fun getProduct(categoryId: String):ResultWrapper<List<Product?>?> {
//        return getProductUseCases.repository.getProducts(categoryId) // Fetch the product from the repository or data source
//    }

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