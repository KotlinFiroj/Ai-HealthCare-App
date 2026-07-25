package com.mediai.enterprise.feature.home.presentation

import app.cash.turbine.test
import com.mediai.enterprise.core.testing.rules.MainDispatcherRule
import com.mediai.enterprise.feature.home.domain.model.DashboardData
import com.mediai.enterprise.feature.home.domain.usecase.GetDashboardDataUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getDashboardDataUseCase = mockk<GetDashboardDataUseCase>()

    @Test
    fun `loadDashboard updates state with data on success`() = runTest {
        val mockData = DashboardData(85, emptyList(), emptyList())
        coEvery { getDashboardDataUseCase() } returns flowOf(Result.success(mockData))

        val viewModel = HomeViewModel(getDashboardDataUseCase)

        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(mockData, state.data)
        }
    }

    @Test
    fun `loadDashboard updates state with error on failure`() = runTest {
        val errorMessage = "Network Error"
        coEvery { getDashboardDataUseCase() } returns flowOf(Result.failure(Exception(errorMessage)))

        val viewModel = HomeViewModel(getDashboardDataUseCase)

        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(errorMessage, state.error)
        }
    }
}
