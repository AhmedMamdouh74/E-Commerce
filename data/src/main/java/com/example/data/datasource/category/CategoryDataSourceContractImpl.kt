package com.example.data.datasource.category

import com.example.data.api.WebServices
import com.example.data.datasourcecontract.CategoryDataSourceContract
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class CategoryDataSourceContractImpl @Inject constructor(private val webServices: WebServices) :
    CategoryDataSourceContract {
    override suspend fun getCategories(): Flow<ResultWrapper<List<Category?>?>> {
        return safeApiCall { webServices.getCategories() }
    }

}