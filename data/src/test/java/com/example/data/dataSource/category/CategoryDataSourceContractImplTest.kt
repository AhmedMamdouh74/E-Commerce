package com.example.data.dataSource.category

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.CategoryDataSourceContract
import com.example.data.model.BaseResponse
import com.example.data.model.category.CategoryDto
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class CategoryDataSourceContractImplTest {
    private lateinit var categoryDataSource: CategoryDataSourceContract
    private val webServices = mockk<WebServices>()

    @Before
    fun setUp() {
        categoryDataSource = CategoryDataSourceContractImpl(webServices)
    }

    @Test
    fun `when call getCategories from data source it should get data from api`() = runTest {
        val cateList = listOf(
            Category(),
            Category(),
            Category()
        )
        val categoriesResponse = BaseResponse<List<Category?>?>(data = cateList)
        coEvery { webServices.getCategories() } returns categoriesResponse
        val result = categoryDataSource.getCategories()
        result as Flow<ResultWrapper.Success<List<Category?>?>>
        assertEquals(3, categoriesResponse.data?.size)
        // coVerify(exactly = 1) { webServices.getCategories() }


    }
}