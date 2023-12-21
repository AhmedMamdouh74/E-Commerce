package com.example.data.dataSource.subCategories

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.SubCategoriesDataSourceContract
import com.example.data.saveApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.SubCategories
import javax.inject.Inject

class SubCategoriesDataSourceContractImpl @Inject constructor(val webServices: WebServices) :
    SubCategoriesDataSourceContract {
    override suspend fun getSubCategories(categoryId: String): ResultWrapper<List<SubCategories?>?> {
        return saveApiCall {
            webServices.getSubCategories(categoryId).data



        }
    }
}