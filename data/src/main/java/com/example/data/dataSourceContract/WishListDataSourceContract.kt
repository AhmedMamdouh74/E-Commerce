package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.model.wishlist.AddToWishlistResponse
import com.example.domain.model.wishlist.WishlistResponse
import kotlinx.coroutines.flow.Flow

interface WishListDataSourceContract {
    suspend fun addProductToWishList(
        token: String,
        productId: String
    ):  Flow<ResultWrapper<List<String?>?>>

    suspend fun getLoggedUserWishList(token: String): Flow<ResultWrapper<List<Product?>?>>
    suspend fun removeProductFromWishlist(
        token: String,
        productId: String
    ): Flow<ResultWrapper<Any>>
}