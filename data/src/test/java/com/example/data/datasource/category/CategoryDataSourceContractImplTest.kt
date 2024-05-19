package com.example.data.datasource.category

import com.example.data.api.WebServices
import com.example.data.datasourcecontract.CategoryDataSourceContract
import com.example.data.model.BaseResponse
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest


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

        webServices.getCategories()
        coVerify(exactly = 1) { webServices.getCategories() }
        val result =
            categoryDataSource.getCategories()
                .filterIsInstance<ResultWrapper.Success<List<Category?>?>>()
                .first()

        assertEquals(3, result.data?.size
    )


}
}