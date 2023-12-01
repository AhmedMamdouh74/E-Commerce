package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.model.SubCategories

interface SubCategoriesDataSource {
    suspend fun getSubCategories(categoryId: String):ResultWrapper<List<SubCategories?>?>
}