package com.example.data.datasourcecontract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.cart.CartResponse
import com.example.domain.model.cart.loggedCart.CartQuantity
import kotlinx.coroutines.flow.Flow

interface CartDataSourceContact {

    suspend fun addProductToCart(token: String, productId: String): Flow<ResultWrapper<CartResponse?>>
    suspend fun removeProductFromCart(token: String, productId: String):Flow<ResultWrapper<Any>>
    suspend fun getLoggedUserCart(token: String): Flow<ResultWrapper<CartQuantity?>>

}