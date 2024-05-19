package com.example.data.datasource.cart

import com.example.data.api.WebServices
import com.example.data.datasourcecontract.CartDataSourceContact
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.cart.CartResponse
import com.example.domain.model.cart.loggedCart.CartQuantity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartDataSourceContactImpl @Inject constructor (private val webServices: WebServices) : CartDataSourceContact {
    override suspend fun addProductToCart(
        token: String,
        productId: String
    ): Flow<ResultWrapper<CartResponse?>> {
        return safeApiCall { webServices.addProductToCart(token, productId) }
    }

    override suspend fun removeProductFromCart(token: String, productId: String):Flow<ResultWrapper<Any>> {
        return safeApiCall { webServices.removeProductFromCart(token, productId) }
    }

    override suspend fun getLoggedUserCart(token: String): Flow<ResultWrapper<CartQuantity?>>{
        return safeApiCall { webServices.getLoggedUserCart(token) }
    }
}