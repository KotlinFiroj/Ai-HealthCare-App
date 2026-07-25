package com.mediai.enterprise.feature.home.data.repository

import com.mediai.enterprise.feature.home.data.remote.HomeApiService
import com.mediai.enterprise.feature.home.domain.model.*
import com.mediai.enterprise.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: HomeApiService
) : HomeRepository {
    override fun getDashboardData(): Flow<Result<DashboardData>> = flow {
        try {
            val response = apiService.getHealthSummary()
            val mockData = DashboardData(
                healthScore = response.health_score,
                metrics = listOf(
                    HealthMetric(MetricType.STEPS, "8,432", "steps", Trend.UP),
                    HealthMetric(MetricType.WATER, "1.5", "L", Trend.STABLE),
                    HealthMetric(MetricType.SLEEP, "7.5", "hrs", Trend.DOWN)
                ),
                suggestions = listOf(
                    AiSuggestion("Health Summary", response.summary_text, Priority.MEDIUM)
                ),
                upcomingAppointment = "Dr. Sarah Smith at 2:00 PM Tomorrow"
            )
            emit(Result.success(mockData))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
