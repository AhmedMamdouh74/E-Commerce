package com.example.domain.repositories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.model.SubCategories
import kotlinx.coroutines.flow.Flow

interface SubCategoriesRepository {
    suspend fun getSubCategories(categoryId: String):Flow<ResultWrapper<List<SubCategories?>?>>
}