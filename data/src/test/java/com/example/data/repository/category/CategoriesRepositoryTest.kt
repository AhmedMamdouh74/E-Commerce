package com.example.data.repository.category

import com.example.data.datasourcecontract.CategoryDataSourceContract
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.repositories.CategoriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test

class CategoriesRepositoryTest {
    private lateinit var categoriesRepository: CategoriesRepository
    private val categoryDataSourceContract = mockk<CategoryDataSourceContract>()

    @Before
    fun setUp() {
        categoriesRepository = CategoriesRepositoryImpl(categoryDataSourceContract)
    }

    @Test
    fun `verify when call categories getData it should call Categories data source`() = runTest {
        val catsList = listOf(
            Category(),
            Category()
        )

        val response = ResultWrapper.Success<List<Category?>?>(data = catsList)
        coEvery { categoriesRepository.getCategories() } returns flowOf(
            response
        )

        val result = categoriesRepository.getCategories()
          //  .filterIsInstance<ResultWrapper.Success<List<Category?>?>>()
            .first()
        result as ResultWrapper.Success

        coVerify(exactly = 1) { categoriesRepository.getCategories() }
        assertEquals(
            2, result.data?.size
        )


    }


}