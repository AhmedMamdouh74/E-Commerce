package com.example.data.dataSource.product

import com.example.data.dataSourceContract.ProductDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun provideProductDataSource(
        productDataSourceImpl: ProductDataSourceImpl
    ): ProductDataSource
}