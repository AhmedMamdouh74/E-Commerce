package com.example.data.repository.category

import com.example.data.dataSourceContract.CategoryDataSourceContract
import com.example.domain.repositories.CategoriesRepository
import io.mockk.MockK
import io.mockk.mockk
import org.junit.Assert.*

import org.junit.Before

class CategoriesRepositoryTest {
     private lateinit var  categoriesRepository:CategoriesRepository
     private val categoryDataSourceContract=mockk<CategoryDataSourceContract>()

    @Before
    fun setUp() {
        categoriesRepository=CategoriesRepositoryImpl(categoryDataSourceContract)
    }

}