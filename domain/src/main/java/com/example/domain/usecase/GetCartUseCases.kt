package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.cart.Cart
import com.example.domain.model.cart.CartResponse
import com.example.domain.model.cart.loggedCart.CartQuantity
import com.example.domain.repositories.CartRepository
import javax.inject.Inject

class GetLoggedUserCartUseCases @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(
        token: String
    ): ResultWrapper<CartQuantity?> {
        return cartRepository.getLoggedUserCart(token)
    }
}

class AddProductToCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(token: String, productId: String): ResultWrapper<CartResponse?> {
        return cartRepository.addProductToCart(token, productId)
    }
}

class RemoveProductFromCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(token: String, productId: String) {
        cartRepository.removeProductFromCart(token, productId)
    }
}
