package com.mediai.enterprise.feature.home.data.repository

import com.mediai.enterprise.feature.home.domain.model.*
import com.mediai.enterprise.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor() : HomeRepository {
    override fun getDashboardData(): Flow<Result<DashboardData>> = flow {
        // Simulating network delay
        delay(1000)

        val mockData = DashboardData(
            healthScore = 85,
            metrics = listOf(
                HealthMetric(MetricType.STEPS, "8,432", "steps", Trend.UP),
                HealthMetric(MetricType.WATER, "1.5", "L", Trend.STABLE),
                HealthMetric(MetricType.SLEEP, "7.5", "hrs", Trend.DOWN),
                HealthMetric(MetricType.HEART_RATE, "72", "bpm", Trend.STABLE)
            ),
            suggestions = listOf(
                AiSuggestion("Increase Hydration", "You've only had 1.5L of water today. Try to reach 2.5L.", Priority.MEDIUM),
                AiSuggestion("Early Bedtime", "Your sleep trend is down. Aim for 8 hours tonight.", Priority.HIGH)
            ),
            upcomingAppointment = "Dr. Sarah Smith at 2:00 PM Tomorrow"
        )
        emit(Result.success(mockData))
    }
}
