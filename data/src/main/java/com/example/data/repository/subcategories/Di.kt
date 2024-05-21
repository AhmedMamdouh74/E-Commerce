package com.example.data.repository.subcategories

import com.example.domain.repositories.SubCategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun bindSubCategoriesRepos(
        repositoryImpl: SubCategoriesRepositoryImpl
    ): SubCategoriesRepository
}