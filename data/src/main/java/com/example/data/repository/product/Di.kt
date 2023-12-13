package com.example.data.repository.product

import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.domain.repositories.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun provideProductsRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

}