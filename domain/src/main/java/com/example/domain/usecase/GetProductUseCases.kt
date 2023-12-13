package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.repositories.ProductRepository
import javax.inject.Inject

class GetProductUseCases @Inject constructor( val repository: ProductRepository) {
    suspend fun invoke(categoryId:String):ResultWrapper<List<Product?>?>{

        return repository.getProducts(categoryId)
    }
}
class GetSpecificProductUseCases @Inject constructor( val repository: ProductRepository) {
    suspend fun invoke(productId:String):ResultWrapper<Product?>{

        return repository.getSpecificProducts(productId)
    }
}