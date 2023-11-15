package com.example.data.repository.category

import com.example.domain.repositories.CategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun bindCategoriesRepos(
        repositoryImpl: CategoriesRepositoryImpl
    ): CategoriesRepository
}