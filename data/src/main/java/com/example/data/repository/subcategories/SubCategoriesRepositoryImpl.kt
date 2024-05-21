package com.example.data.repository.subcategories

import com.example.data.datasourcecontract.SubCategoriesDataSourceContract
import com.example.domain.common.ResultWrapper
import com.example.domain.model.SubCategories
import com.example.domain.repositories.SubCategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubCategoriesRepositoryImpl @Inject constructor (
    private val categoryDataSource: SubCategoriesDataSourceContract
): SubCategoriesRepository {
    override suspend fun getSubCategories(categoryid: String): Flow<ResultWrapper<List<SubCategories?>?>> {
        return categoryDataSource.getSubCategories(categoryid)
    }


}