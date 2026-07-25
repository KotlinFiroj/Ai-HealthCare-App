package com.mediai.enterprise.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediai.enterprise.core.analytics.AnalyticsHelper
import com.mediai.enterprise.feature.home.domain.model.DashboardData
import com.mediai.enterprise.feature.home.domain.usecase.GetDashboardDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDashboardDataUseCase: GetDashboardDataUseCase,
    private val analyticsHelper: AnalyticsHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadDashboard()
        analyticsHelper.logEvent("home_screen_viewed")
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            Timber.d("Loading dashboard data...")
            getDashboardDataUseCase().collect { result ->
                if (result.isSuccess) {
                    _uiState.update { it.copy(isLoading = false, data = result.getOrNull()) }
                    analyticsHelper.logEvent("dashboard_load_success")
                } else {
                    val error = result.exceptionOrNull()?.message
                    _uiState.update { it.copy(isLoading = false, error = error) }
                    Timber.e(result.exceptionOrNull(), "Failed to load dashboard")
                    analyticsHelper.logEvent("dashboard_load_failure", mapOf("error" to (error ?: "unknown")))
                }
            }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val data: DashboardData? = null,
    val error: String? = null
)
