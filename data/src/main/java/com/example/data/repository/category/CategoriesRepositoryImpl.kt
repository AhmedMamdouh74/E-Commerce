package com.example.data.repository.category

import com.example.data.datasourcecontract.CategoryDataSourceContract
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.repositories.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoryDataSourceContract: CategoryDataSourceContract
) : CategoriesRepository {
    override suspend fun getCategories(page: Int): Flow<ResultWrapper<List<Category?>?>> {
        return categoryDataSourceContract.getCategories()

    }


}