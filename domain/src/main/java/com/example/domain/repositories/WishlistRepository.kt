package com.example.domain.repositories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface WishlistRepository {
    suspend fun addProductToWishlist(
        token: String,
        productId: String
    ): Flow<ResultWrapper<List<String?>?>>

    suspend fun getLoggedUserWishlist(token: String): Flow<ResultWrapper<List<Product?>?>>
    suspend fun removeProductToWishlist(token: String, productId: String): Flow<ResultWrapper<Any>>
}