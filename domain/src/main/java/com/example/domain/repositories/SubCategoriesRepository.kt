package com.example.domain.repositories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.model.SubCategories

interface SubCategoriesRepository {
    suspend fun getSubCategories(categoryId: String):ResultWrapper<List<SubCategories?>?>
}