package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.cart.Cart
import com.example.domain.model.cart.CartResponse
import com.example.domain.model.cart.loggedCart.CartQuantity
import com.example.domain.repositories.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoggedUserCartUseCases @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(
        token: String
    ): Flow<ResultWrapper<CartQuantity?>> {
        return cartRepository.getLoggedUserCart(token)
    }
}

class AddProductToCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(token: String, productId: String):Flow<ResultWrapper<CartResponse?>> {
        return cartRepository.addProductToCart(token, productId)
    }
}

class RemoveProductFromCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(token: String, productId: String):Flow<ResultWrapper<Any>> {
       return cartRepository.removeProductFromCart(token, productId)
    }
}
