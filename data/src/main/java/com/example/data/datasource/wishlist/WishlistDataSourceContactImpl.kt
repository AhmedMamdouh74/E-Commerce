package com.example.data.datasource.wishlist

import com.example.data.api.WebServices
import com.example.data.datasourcecontract.WishListDataSourceContract

import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishlistDataSourceContactImpl @Inject constructor(private val webServices: WebServices) :
    WishListDataSourceContract {
    override suspend fun addProductToWishList(
        token: String,
        productId: String
    ): Flow<ResultWrapper<List<String?>?>> {
        return safeApiCall {
            webServices.addProductToWishlist(token, productId) }
    }

    override suspend fun getLoggedUserWishList(token: String): Flow<ResultWrapper<List<Product?>?>> {
        return safeApiCall { webServices.getLoggedUserWishlist(token) }
    }

    override suspend fun removeProductFromWishlist(
        token: String,
        productId: String
    ): Flow<ResultWrapper<Any>> {
        return safeApiCall {
            webServices.removeProductFromWishlist(productId, token)
        }
    }
}