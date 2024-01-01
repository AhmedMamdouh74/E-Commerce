package com.example.data.dataSource.cart

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.CartDataSourceContact
import com.example.data.saveApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.CartResponse

class CartDataSourceContactImpl(private val webServices: WebServices) : CartDataSourceContact {
    override suspend fun addProductToCart(
        token: String,
        productId: String
    ): ResultWrapper<CartResponse?> {
        return saveApiCall { webServices.addProductToCart(token, productId).data }
    }

    override suspend fun removeProductFromCart(token: String, productId: String): Any {
        return saveApiCall { webServices.removeProductFromCart(token, productId) }
    }

    override suspend fun getLoggedUserCart(token: String): ResultWrapper<List<CartResponse?>?> {
        return saveApiCall { webServices.getLoggedUserCart(token).data }
    }
}