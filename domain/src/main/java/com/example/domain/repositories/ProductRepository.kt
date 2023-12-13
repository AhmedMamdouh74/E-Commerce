package com.example.domain.repositories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(categoryId:String): ResultWrapper<List<Product?>?>
    suspend fun getSpecificProducts(productId:String): ResultWrapper<Product?>
}