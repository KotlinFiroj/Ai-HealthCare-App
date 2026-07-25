package com.mediai.enterprise.feature.analytics.domain.usecase

import com.mediai.enterprise.feature.analytics.domain.model.WellnessPlan
import com.mediai.enterprise.feature.analytics.domain.repository.AnalyticsRepository
import javax.inject.Inject

class GetWellnessPlanUseCase @Inject constructor(
    private val repository: AnalyticsRepository
) {
    suspend operator fun invoke(profile: String): Result<WellnessPlan> {
        return repository.getWellnessPlan(profile)
    }
}
