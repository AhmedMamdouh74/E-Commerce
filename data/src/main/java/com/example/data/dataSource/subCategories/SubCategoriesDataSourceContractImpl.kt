package com.example.data.dataSource.subCategories

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.SubCategoriesDataSourceContract
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.SubCategories
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubCategoriesDataSourceContractImpl @Inject constructor(val webServices: WebServices) :
    SubCategoriesDataSourceContract {
    override suspend fun getSubCategories(categoryId: String): Flow<ResultWrapper<List<SubCategories?>?>> {
        return safeApiCall {
            webServices.getSubCategories(categoryId)



        }
    }
}