package com.example.e_commerce.ui.features.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.TokenManager
import com.example.domain.common.ResultWrapper
import com.example.domain.usecase.GetLoggedUserCartUseCases
import com.example.domain.usecase.RemoveProductFromCartUseCase
import com.example.e_commerce.ui.utils.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    tokenManager: TokenManager,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    private val getLoggedUserCartUseCases: GetLoggedUserCartUseCases,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), CartContract.ViewModel {
    private var _event = MutableLiveData<CartContract.Event>()
    override val event = _event
    private var _state = MutableStateFlow<CartContract.State>(CartContract.State.Loading("loading"))
    override val state = _state
    val token = tokenManager.getToken().toString()



    override fun handleAction(action: CartContract.Action) {
        when (action) {
            CartContract.Action.LoadingLoggedUserCarts -> loadingLoggedUserCarts()
            is CartContract.Action.RemoveProductFromCart -> removeProductFromCart(
                action.token,
                action.productId
            )


        }
    }

    private fun removeProductFromCart(token: String, productId: String) {
        viewModelScope.launch(ioDispatcher) {
            removeProductFromCartUseCase.invoke(token, productId)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> _state.emit(CartContract.State.Error(response.error.localizedMessage))
                        ResultWrapper.Loading -> _state.emit(CartContract.State.Loading("loading"))
                        is ResultWrapper.ServerError -> _state.emit(
                            CartContract.State.Error(
                                response.error.serverMessage
                            )
                        )

                        is ResultWrapper.Success -> _state.emit(CartContract.State.Idle)

                        else -> {}
                    }
                }
        }
    }


    private fun loadingLoggedUserCarts() {
        viewModelScope.launch(ioDispatcher) {
            getLoggedUserCartUseCases.invoke(token)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> _state.emit(CartContract.State.Error(response.error.localizedMessage))
                        ResultWrapper.Loading -> _state.emit(CartContract.State.Loading("loading"))
                        is ResultWrapper.ServerError -> _state.emit(
                            CartContract.State.Error(
                                response.error.serverMessage
                            )
                        )

                        is ResultWrapper.Success -> _state.emit(CartContract.State.Success(response.data))

                        else -> {}
                    }
                }
        }
    }



}