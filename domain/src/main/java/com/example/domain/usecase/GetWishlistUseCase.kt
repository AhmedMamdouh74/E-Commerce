package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.repositories.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddProductToWishlistUseCase @Inject constructor(private val wishlistRepository: WishlistRepository) {
    suspend operator fun invoke(
        token: String,
        productId: String
    ): Flow<ResultWrapper<List<String?>?>> {
        return wishlistRepository.addProductToWishlist(token, productId)
    }
}

class RemoveProductFromWishlistUseCase @Inject constructor(private val wishlistRepository: WishlistRepository) {
    suspend operator fun invoke(productId: String, token: String):Flow<ResultWrapper<Any>> {
       return  wishlistRepository.removeProductToWishlist(token, productId)
    }
}

class GetLoggedUserWishlistUseCase @Inject constructor(private val wishlistRepository: WishlistRepository) {
    suspend operator fun invoke(token: String):Flow<ResultWrapper<List<Product?>?>> {
        return wishlistRepository.getLoggedUserWishlist(token)
    }
}

