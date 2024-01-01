package com.example.domain.repositories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.CartResponse
interface CartRepository {
    suspend fun addProductToCart(token: String, productId: String): ResultWrapper<CartResponse?>
    suspend fun removeProductFromCart(token: String,productId: String): Any
    suspend fun getLoggedUserCart(token: String): ResultWrapper<List<CartResponse?>?>
}