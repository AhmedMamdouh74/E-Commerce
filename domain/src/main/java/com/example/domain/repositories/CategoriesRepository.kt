package com.example.domain.repositories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category

interface CategoriesRepository {
   suspend fun getCategories(page: Int = 1):  ResultWrapper<List<Category?>?>
}