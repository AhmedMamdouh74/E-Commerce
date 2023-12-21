package com.example.data.dataSource.product

import com.example.data.dataSourceContract.ProductDataSourceContract
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