package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.model.WishlistResponse
import com.example.domain.repositories.WishlistRepository
import javax.inject.Inject

class AddProductToWishlistUseCase @Inject constructor(private val wishlistRepository: WishlistRepository) {
    suspend operator fun invoke(
        token: String,
        productId: String
    ): ResultWrapper<WishlistResponse?> {
        return wishlistRepository.addProductToWishlist(token, productId)
    }
}

class RemoveProductFromWishlistUseCase @Inject constructor(private val wishlistRepository: WishlistRepository) {
    suspend operator fun invoke(productId: String, token: String) {
         wishlistRepository.removeProductToWishlist(productId, token)
    }
}

class GetLoggedUserWishlistUseCase @Inject constructor(private val wishlistRepository: WishlistRepository) {
    suspend operator fun invoke(token: String): ResultWrapper<List<Product?>?> {
        return wishlistRepository.getLoggedUserWishlist(token)
    }
}

