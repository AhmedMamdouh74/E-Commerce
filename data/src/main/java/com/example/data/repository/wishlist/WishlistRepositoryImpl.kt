package com.example.data.repository.wishlist

import com.example.data.dataSourceContract.WishListDataSourceContract
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.model.WishlistResponse
import com.example.domain.repositories.WishlistRepository
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(private val wishListDataSourceContract: WishListDataSourceContract):WishlistRepository {
    override suspend fun addProductToWishlist(
        token: String,
        productId: String
    ): ResultWrapper<WishlistResponse?> {
        return wishListDataSourceContract.addProductToWishList(token,productId)
    }

    override suspend fun getLoggedUserWishlist(token: String): ResultWrapper<List<Product?>?> {
        return wishListDataSourceContract.getLoggedUserWishList(token)
    }

    override suspend fun removeProductToWishlist(token: String, productId: String): Any {
        return wishListDataSourceContract.removeProductFromWishlist(token, productId)
    }
}