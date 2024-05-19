package com.example.data.repository.wishlist

import com.example.data.datasourcecontract.WishListDataSourceContract
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.repositories.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(private val wishListDataSourceContract: WishListDataSourceContract):WishlistRepository {
    override suspend fun addProductToWishlist(
        token: String,
        productId: String
    ):Flow<ResultWrapper<List<String?>?>> {
        return wishListDataSourceContract.addProductToWishList(token,productId)
    }

    override suspend fun getLoggedUserWishlist(token: String): Flow<ResultWrapper<List<Product?>?>> {
        return wishListDataSourceContract.getLoggedUserWishList(token)
    }

    override suspend fun removeProductToWishlist(token: String, productId: String): Flow<ResultWrapper<Any>> {
        return wishListDataSourceContract.removeProductFromWishlist(token, productId)
    }
}