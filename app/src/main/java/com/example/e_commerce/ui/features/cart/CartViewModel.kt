package com.example.e_commerce.ui.features.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.TokenManager
import com.example.domain.common.ResultWrapper
import com.example.domain.usecase.GetLoggedUserCartUseCases
import com.example.domain.usecase.RemoveProductFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    tokenManager: TokenManager,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    private val getLoggedUserCartUseCases: GetLoggedUserCartUseCases,
) : ViewModel(), CartContract.ViewModel {
    private var _event = MutableLiveData<CartContract.Event>()
    override val event = _event
    private var _state = MutableLiveData<CartContract.State>()
    override val state = _state


    override fun handleAction(action: CartContract.Action) {
        when (action) {
            CartContract.Action.LoadingLoggedUserCarts -> loadingLoggedUserCarts()
            is CartContract.Action.RemoveProductFromCart -> removeProductFromCart(
                action.token,
                action.productId
            )

            else -> {}
        }


    }

    private fun removeProductFromCart(token: String, productId: String) {
        viewModelScope.launch {
            removeProductFromCartUseCase.invoke(token, productId)
            _state.postValue(CartContract.State.Idle)
        }
    }

    private val token = tokenManager.getToken().toString()

    private fun loadingLoggedUserCarts() {
        viewModelScope.launch {
            _state.postValue(CartContract.State.Loading("Loading"))
            val response = getLoggedUserCartUseCases.invoke(token)
            when (response) {
                is ResultWrapper.Error -> _state.postValue(CartContract.State.Error(response.error.localizedMessage))
                ResultWrapper.Loading -> {}
                is ResultWrapper.ServerError -> _state.postValue(CartContract.State.Error(response.error.serverMessage))
                is ResultWrapper.Success -> _state.postValue(CartContract.State.Success(response.data))

                else -> {}
            }
        }

    }
}