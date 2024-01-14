package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.model.SubCategories
import com.example.domain.repositories.SubCategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubCategoriesUseCases @Inject constructor (val subCategories: SubCategoriesRepository) {
    suspend operator fun invoke(categoryId: String):Flow<ResultWrapper<List<SubCategories?>?>>{
        return subCategories.getSubCategories(categoryId)
    }
}