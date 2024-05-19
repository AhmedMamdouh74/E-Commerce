package com.example.data.repository.cart

import com.example.data.datasourcecontract.CartDataSourceContact
import com.example.domain.common.ResultWrapper
import com.example.domain.model.cart.CartResponse
import com.example.domain.model.cart.loggedCart.CartQuantity
import com.example.domain.repositories.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartDataSourceContact: CartDataSourceContact) :
    CartRepository {
    override suspend fun addProductToCart(
        token: String,
        productId: String
    ): Flow<ResultWrapper<CartResponse?>> {
        return cartDataSourceContact.addProductToCart(token, productId)
    }

    override suspend fun removeProductFromCart(token: String, productId: String): Flow<ResultWrapper<Any>> {
        return cartDataSourceContact.removeProductFromCart(token, productId)
    }

    override suspend fun getLoggedUserCart(token: String): Flow<ResultWrapper<CartQuantity?>> {
        return cartDataSourceContact.getLoggedUserCart(token)
    }
}