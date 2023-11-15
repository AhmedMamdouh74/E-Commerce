package com.example.domain.repositories

import com.example.domain.model.Category

interface CategoriesRepository {
   suspend fun getCategories(page: Int = 1): List<Category?>?
}