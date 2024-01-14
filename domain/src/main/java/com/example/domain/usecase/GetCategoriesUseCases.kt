package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.repositories.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCases @Inject constructor( val repository: CategoriesRepository) {
    suspend fun  invoke():Flow<ResultWrapper<List<Category?>?>> {
        return repository.getCategories()
    }

}