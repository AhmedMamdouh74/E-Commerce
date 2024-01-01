package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.CartResponse
import com.example.domain.repositories.CartRepository
import javax.inject.Inject

class GetLoggedUserCartUseCases @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(
        token: String
    ): ResultWrapper<List<CartResponse?>?> {
        return cartRepository.getLoggedUserCart(token)
    }
}

class AddProductToCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(token: String, productId: String): ResultWrapper<CartResponse?> {
        return cartRepository.addProductToCart(token, productId)
    }
}

class RemoveProductFromCart @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(token: String, productId: String) {
        cartRepository.removeProductFromCart(token, productId)
    }
}
