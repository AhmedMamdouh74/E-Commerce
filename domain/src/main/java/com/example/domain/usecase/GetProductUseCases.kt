package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCases @Inject constructor( val repository: ProductRepository) {
    suspend fun invoke(categoryId:String):Flow<ResultWrapper<List<Product?>?>>{

        return repository.getProducts(categoryId)
    }
}
class GetSpecificProductUseCases @Inject constructor( val repository: ProductRepository) {
    suspend fun invoke(productId:String):Flow<ResultWrapper<Product?>>{

        return repository.getSpecificProducts(productId)
    }
}