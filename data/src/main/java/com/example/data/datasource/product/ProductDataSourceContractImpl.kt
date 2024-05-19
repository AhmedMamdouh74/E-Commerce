package com.example.data.datasource.product

import com.example.data.api.WebServices
import com.example.data.datasourcecontract.ProductDataSourceContract
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductDataSourceContractImpl @Inject constructor(private val webServices: WebServices):ProductDataSourceContract {
    override suspend fun getProducts(categoryId: String): Flow<ResultWrapper<List<Product?>?>> {
        return safeApiCall {
            webServices.getProducts(categoryId)
        }
    }

    override suspend fun getSpecificProducts(productId: String): Flow<ResultWrapper<Product?>> {
        return safeApiCall {
            webServices.getSpecificProducts(productId)
        }
    }

}