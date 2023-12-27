package com.example.domain.repositories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.model.WishlistResponse

interface WishlistRepository {
suspend fun addProductToWishlist(token:String,productId:String):ResultWrapper<WishlistResponse?>
suspend fun getLoggedUserWishlist(token:String):ResultWrapper<List<Product?>?>
suspend fun removeProductToWishlist(token:String,productId:String):Any
}