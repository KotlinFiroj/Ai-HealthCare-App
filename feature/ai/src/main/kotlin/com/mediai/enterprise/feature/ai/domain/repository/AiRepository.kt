package com.mediai.enterprise.feature.ai.domain.repository

import com.mediai.enterprise.feature.ai.domain.model.RiskPrediction
import com.mediai.enterprise.feature.ai.domain.model.SymptomAssessment
import kotlinx.coroutines.flow.Flow

interface AiRepository {
    suspend fun assessSymptoms(symptoms: String): Result<SymptomAssessment>
    suspend fun getRiskPredictions(userData: String): Result<List<RiskPrediction>>
}
