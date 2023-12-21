package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product

interface ProductDataSourceContract {
    suspend fun getProducts(categoryId:String):ResultWrapper<List<Product?>?>
    suspend fun getSpecificProducts(categoryId:String):ResultWrapper<Product?>
}