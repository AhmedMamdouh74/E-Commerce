package com.example.e_commerce.ui.home.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.common.ResultWrapper
import com.example.domain.exceptions.ServerError
import com.example.domain.model.Category
import com.example.domain.usecase.GetCategoriesUseCases
import com.example.e_commerce.utils.getOrAwaitValueTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CategoriesViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var categoriesViewModel: CategoriesContract.ViewModel
    private val getCategoriesUseCases = mockk<GetCategoriesUseCases>()


    @Before
    fun setUp() {
        categoriesViewModel = CategoriesViewModel(getCategoriesUseCases, Dispatchers.Unconfined)
    }

    @Test
    fun `when call load categories with Loading response it should emit loading state`() = runTest {
        val response = ResultWrapper.Loading
        coEvery { getCategoriesUseCases.invoke() } returns flowOf(response)
        categoriesViewModel.handleAction(CategoriesContract.Action.LoadingCategories)
        val state = categoriesViewModel.states.first()
        state as CategoriesContract.State.Loading
        assertEquals("loading", state.message)
    }

    @Test
    fun `when call load categories with success response it should emit success state`() = runTest {
        val response = ResultWrapper.Success<List<Category?>?>(data = listOf(Category()))
        coEvery { getCategoriesUseCases.invoke() } returns flowOf(response)
        categoriesViewModel.handleAction(CategoriesContract.Action.LoadingCategories)
        val state = categoriesViewModel.states.first()
        state as CategoriesContract.State.Success
        assertEquals(1, state.category.size)
    }

    @Test
    fun `when call load categories with serverError response it should emit Error state`() =
        runTest {
            val response = ResultWrapper.ServerError(
                ServerError(
                    status = "error",
                    serverMessage = "errorLoadingState",
                    statusCode = 400
                )
            )
            coEvery { getCategoriesUseCases.invoke() } returns flowOf(response)
            categoriesViewModel.handleAction(CategoriesContract.Action.LoadingCategories)
            val state = categoriesViewModel.states.first()
            state as CategoriesContract.State.Error
            assertEquals("errorLoadingState", state.message)
        }

    @Test
    fun `when call load categories with Error response it should emit Error state`() =
        runTest {
            val response = ResultWrapper.Error(Exception("error"))
            coEvery { getCategoriesUseCases.invoke() } returns flowOf(response)
            categoriesViewModel.handleAction(CategoriesContract.Action.LoadingCategories)
            val state = categoriesViewModel.states.first()
            state as CategoriesContract.State.Error
            assertEquals("error", state.message)
        }

    @Test
    fun `when call handle action with categoryClicked it should emit navigateToSubCategory event`() {
        categoriesViewModel.handleAction(CategoriesContract.Action.CategoryClicked(Category()))
        val event = categoriesViewModel.events.getOrAwaitValueTest()
        assertTrue(event is CategoriesContract.Event.NavigateToSubCategories)

    }

    @Test
    fun `when call handle action with CartClicked it should emit navigateToCartActivity event`() {
        categoriesViewModel.handleAction(CategoriesContract.Action.CartClicked)
        val event = categoriesViewModel.events.getOrAwaitValueTest()
        assertTrue(event is CategoriesContract.Event.NavigateToCart)

    }
}