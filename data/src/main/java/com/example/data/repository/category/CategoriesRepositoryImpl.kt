package com.example.data.repository.category

import com.example.data.dataSourceContract.CategoryDataSource
import com.example.domain.model.Category
import com.example.domain.repositories.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor (
    private val categoryDataSource: CategoryDataSource
):CategoriesRepository {
    override suspend fun getCategories(page: Int): List<Category?>? {
         return categoryDataSource.getCategories()

    }


}