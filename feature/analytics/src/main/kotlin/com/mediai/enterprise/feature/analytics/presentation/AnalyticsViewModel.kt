package com.mediai.enterprise.feature.analytics.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediai.enterprise.feature.analytics.domain.model.*
import com.mediai.enterprise.feature.analytics.domain.usecase.GetHealthTrendsUseCase
import com.mediai.enterprise.feature.analytics.domain.usecase.GetWellnessPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val getWellnessPlanUseCase: GetWellnessPlanUseCase,
    private val getHealthTrendsUseCase: GetHealthTrendsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnalyticsUiState())
    val uiState = _uiState.asStateFlow()

    fun loadWellnessPlan() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingPlan = true, error = null) }
            val result = getWellnessPlanUseCase("mock_user_profile")
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoadingPlan = false, wellnessPlan = result.getOrNull()) }
            } else {
                _uiState.update { it.copy(isLoadingPlan = false, error = result.exceptionOrNull()?.message) }
            }
        }
    }

    fun loadTrends() {
        getHealthTrendsUseCase()
            .onStart { _uiState.update { it.copy(isLoadingTrends = true) } }
            .onEach { trends ->
                _uiState.update { it.copy(isLoadingTrends = false, trends = trends) }
            }
            .launchIn(viewModelScope)
    }

    fun toggleGoal(goal: DailyGoal, isCompleted: Boolean) {
        val currentPlan = _uiState.value.wellnessPlan ?: return
        val updatedGoals = currentPlan.dailyGoals.map {
            if (it.title == goal.title) it.copy(isCompleted = isCompleted) else it
        }
        _uiState.update { it.copy(wellnessPlan = currentPlan.copy(dailyGoals = updatedGoals)) }
    }
}

data class AnalyticsUiState(
    val wellnessPlan: WellnessPlan? = null,
    val trends: List<HealthTrend> = emptyList(),
    val isLoadingPlan: Boolean = false,
    val isLoadingTrends: Boolean = false,
    val error: String? = null
)
