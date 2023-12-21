package com.example.data.dataSource.product

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.ProductDataSourceContract
import com.example.data.saveApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import javax.inject.Inject

class ProductDataSourceContractImpl @Inject constructor(private val webServices: WebServices):ProductDataSourceContract {
    override suspend fun getProducts(categoryId: String): ResultWrapper<List<Product?>?> {
        return saveApiCall {
            webServices.getProducts(categoryId).data
        }
    }

    override suspend fun getSpecificProducts(productId: String): ResultWrapper<Product?> {
        return saveApiCall {
            webServices.getSpecificProducts(productId).data
        }
    }

}