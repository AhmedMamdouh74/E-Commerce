package com.example.data.dataSourceContract


import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryDataSourceContract {
suspend fun getCategories():Flow<ResultWrapper<List<Category?>?>>
}