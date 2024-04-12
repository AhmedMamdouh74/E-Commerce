package com.example.data.repository.product

import com.example.data.datasourcecontract.ProductDataSourceContract
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val productDataSource: ProductDataSourceContract):ProductRepository {
    override suspend fun getProducts(categoryId: String): Flow<ResultWrapper<List<Product?>?>> {
        return productDataSource.getProducts(categoryId)
    }

    override suspend fun getSpecificProducts(productId: String): Flow<ResultWrapper<Product?>> {
        return productDataSource.getSpecificProducts(productId)
    }
}