package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.model.WishlistResponse

interface WishListDataSourceContract {
    suspend fun addProductToWishList(token:String,productId: String) : ResultWrapper<WishlistResponse?>
    suspend fun getLoggedUserWishList(token: String): ResultWrapper<List<Product?>?>
    suspend fun removeProductFromWishlist(token : String,productId: String) : Any
}