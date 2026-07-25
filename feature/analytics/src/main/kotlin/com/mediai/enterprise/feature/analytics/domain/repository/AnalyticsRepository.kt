package com.mediai.enterprise.feature.analytics.domain.repository

import com.mediai.enterprise.feature.analytics.domain.model.HealthTrend
import com.mediai.enterprise.feature.analytics.domain.model.WellnessPlan
import kotlinx.coroutines.flow.Flow

interface AnalyticsRepository {
    suspend fun getWellnessPlan(profile: String): Result<WellnessPlan>
    fun getHealthTrends(): Flow<List<HealthTrend>>
}
