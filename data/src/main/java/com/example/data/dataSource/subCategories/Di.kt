package com.example.data.dataSource.subCategories

import com.example.data.dataSourceContract.SubCategoriesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class Di {
    @Binds
    abstract fun bindsSubCategoriesDataSource(
        subCategoriesDataSourceImpl: SubCategoriesDataSourceImpl
    ): SubCategoriesDataSource

}