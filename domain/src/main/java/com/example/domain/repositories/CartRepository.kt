package com.example.domain.repositories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.cart.Cart
import com.example.domain.model.cart.CartResponse
import com.example.domain.model.cart.loggedCart.CartQuantity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addProductToCart(token: String, productId: String):Flow<ResultWrapper<CartResponse?>>
    suspend fun removeProductFromCart(token: String,productId: String): Flow<ResultWrapper<Any>>
    suspend fun getLoggedUserCart(token: String): Flow<ResultWrapper<CartQuantity?>>
}