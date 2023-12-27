package com.example.data.dataSource.wishlist

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.WishListDataSourceContract
import com.example.data.saveApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.model.WishlistResponse
import javax.inject.Inject

class WishlistDataSourceContactImpl @Inject constructor(private val webServices: WebServices) :
    WishListDataSourceContract {
    override suspend fun addProductToWishList(
        token: String,
        productId: String
    ): ResultWrapper<WishlistResponse?> {
        return saveApiCall { webServices.addProductToWishlist(token, productId).data }
    }

    override suspend fun getLoggedUserWishList(token: String): ResultWrapper<List<Product?>?> {
        return saveApiCall { webServices.getLoggedUserWishlist(token).data }
    }

    override suspend fun removeProductFromWishlist(token: String, productId: String): Any {
        return saveApiCall {
            webServices.removeProductFromWishlist(productId, token)
        }
    }
}