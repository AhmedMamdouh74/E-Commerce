package com.example.data.datasource.product

import com.example.data.datasourcecontract.ProductDataSourceContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun bindProductDataSource(
        productDataSourceImpl: ProductDataSourceContractImpl
    ): ProductDataSourceContract
}