package com.mediai.enterprise.feature.analytics.domain.usecase

import com.mediai.enterprise.feature.analytics.domain.model.HealthTrend
import com.mediai.enterprise.feature.analytics.domain.repository.AnalyticsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHealthTrendsUseCase @Inject constructor(
    private val repository: AnalyticsRepository
) {
    operator fun invoke(): Flow<List<HealthTrend>> {
        return repository.getHealthTrends()
    }
}
