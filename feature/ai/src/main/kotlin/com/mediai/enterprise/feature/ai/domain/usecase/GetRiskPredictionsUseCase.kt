package com.mediai.enterprise.feature.ai.domain.usecase

import com.mediai.enterprise.feature.ai.domain.model.RiskPrediction
import com.mediai.enterprise.feature.ai.domain.repository.AiRepository
import javax.inject.Inject

class GetRiskPredictionsUseCase @Inject constructor(
    private val repository: AiRepository
) {
    suspend operator fun invoke(userData: String): Result<List<RiskPrediction>> {
        return repository.getRiskPredictions(userData)
    }
}
