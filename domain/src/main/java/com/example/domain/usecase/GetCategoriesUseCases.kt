package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.repositories.CategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCases @Inject constructor( val repository: CategoriesRepository) {
    suspend fun  invoke():ResultWrapper<List<Category?>?> {
        return repository.getCategories()
    }

}