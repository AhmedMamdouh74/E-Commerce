package com.example.data.repository.subCategories

import com.example.data.dataSourceContract.CategoryDataSource
import com.example.data.dataSourceContract.SubCategoriesDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.model.SubCategories
import com.example.domain.repositories.CategoriesRepository
import com.example.domain.repositories.SubCategoriesRepository
import javax.inject.Inject

class SubCategoriesRepositoryImpl @Inject constructor (
    private val categoryDataSource: SubCategoriesDataSource
): SubCategoriesRepository {
    override suspend fun getSubCategories(categoryid: String): ResultWrapper<List<SubCategories?>?> {
        return categoryDataSource.getSubCategories(categoryid)
    }


}