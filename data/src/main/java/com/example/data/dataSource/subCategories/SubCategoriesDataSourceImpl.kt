package com.example.data.dataSource.subCategories

import android.util.Log
import com.example.data.api.WebServices
import com.example.data.dataSourceContract.SubCategoriesDataSource
import com.example.data.saveApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.model.SubCategories
import javax.inject.Inject

class SubCategoriesDataSourceImpl @Inject constructor(val webServices: WebServices) :
    SubCategoriesDataSource {
    override suspend fun getSubCategories(categoryId: String): ResultWrapper<List<SubCategories?>?> {
        return saveApiCall {
            webServices.getSubCategories(categoryId).data



        }
    }
}