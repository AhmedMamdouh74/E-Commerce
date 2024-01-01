package com.example.data.repository.cart

import com.example.data.dataSource.cart.CartDataSourceContactImpl
import com.example.domain.common.ResultWrapper
import com.example.domain.model.CartResponse
import com.example.domain.repositories.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartDataSourceContactImpl: CartDataSourceContactImpl) :
    CartRepository {
    override suspend fun addProductToCart(
        token: String,
        productId: String
    ): ResultWrapper<CartResponse?> {
        return cartDataSourceContactImpl.addProductToCart(token, productId)
    }

    override suspend fun removeProductFromCart(token: String, productId: String): Any {
        return cartDataSourceContactImpl.removeProductFromCart(token, productId)
    }

    override suspend fun getLoggedUserCart(token: String): ResultWrapper<List<CartResponse?>?> {
        return cartDataSourceContactImpl.getLoggedUserCart(token)
    }
}