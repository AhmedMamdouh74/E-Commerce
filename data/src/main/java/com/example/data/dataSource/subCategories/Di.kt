package com.example.data.datasource.subCategories

import com.example.data.datasourcecontract.SubCategoriesDataSourceContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class Di {
    @Binds
    abstract fun bindsSubCategoriesDataSource(
        subCategoriesDataSourceImpl: SubCategoriesDataSourceContractImpl
    ): SubCategoriesDataSourceContract

}