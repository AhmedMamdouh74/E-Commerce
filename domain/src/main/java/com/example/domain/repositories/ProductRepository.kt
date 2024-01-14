package com.example.domain.repositories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(categoryId:String):Flow< ResultWrapper<List<Product?>?>>
    suspend fun getSpecificProducts(productId:String):Flow< ResultWrapper<Product?>>
}