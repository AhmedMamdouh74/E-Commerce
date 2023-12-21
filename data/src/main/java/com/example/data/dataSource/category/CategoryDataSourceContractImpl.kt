package com.example.data.dataSource.category

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.CategoryDataSourceContract
import com.example.data.saveApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import javax.inject.Inject

class CategoryDataSourceContractImpl @Inject constructor(private val webServices: WebServices) :
    CategoryDataSourceContract {
    override suspend fun getCategories(): ResultWrapper<List<Category?>?> {
        return saveApiCall {
            webServices.getCategories().data
        }

    }
}