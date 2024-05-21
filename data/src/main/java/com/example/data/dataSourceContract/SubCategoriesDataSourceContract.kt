package com.example.data.datasourcecontract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.SubCategories
import kotlinx.coroutines.flow.Flow

interface SubCategoriesDataSourceContract {
    suspend fun getSubCategories(categoryId: String):Flow<ResultWrapper<List<SubCategories?>?>>
}