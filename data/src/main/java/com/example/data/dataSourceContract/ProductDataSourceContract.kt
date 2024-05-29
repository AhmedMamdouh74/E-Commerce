package com.example.data.datasourcecontract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductDataSourceContract {
    suspend fun getProducts(categoryId:String):Flow<ResultWrapper<List<Product?>?>>
    suspend fun getSpecificProducts(categoryId:String):Flow<ResultWrapper<Product?>>
}