package com.example.data.dataSource.category

import com.example.data.dataSourceContract.CategoryDataSourceContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun bindCategoryDataSource(
        categoryDataSourceImpl: CategoryDataSourceContractImpl
    ): CategoryDataSourceContract

}