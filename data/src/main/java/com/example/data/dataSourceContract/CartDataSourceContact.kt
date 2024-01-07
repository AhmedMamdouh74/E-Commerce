package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.cart.Cart
import com.example.domain.model.cart.CartResponse
import com.example.domain.model.cart.loggedCart.CartQuantity

interface CartDataSourceContact {

    suspend fun addProductToCart(token: String, productId: String): ResultWrapper<CartResponse?>
    suspend fun removeProductFromCart(token: String, productId: String): Any
    suspend fun getLoggedUserCart(token: String): ResultWrapper<CartQuantity?>

}