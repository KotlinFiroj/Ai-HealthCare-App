package com.mediai.enterprise.feature.ai.data.repository

import com.mediai.enterprise.feature.ai.data.remote.AiApiService
import com.mediai.enterprise.feature.ai.data.remote.model.RiskRequestDto
import com.mediai.enterprise.feature.ai.data.remote.model.SymptomRequestDto
import com.mediai.enterprise.feature.ai.domain.model.*
import com.mediai.enterprise.feature.ai.domain.repository.AiRepository
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val apiService: AiApiService
) : AiRepository {

    override suspend fun assessSymptoms(symptoms: String): Result<SymptomAssessment> {
        return try {
            val response = apiService.assessSymptoms(SymptomRequestDto(symptoms))
            Result.success(
                SymptomAssessment(
                    possibleConditions = response.possible_conditions,
                    urgency = UrgencyLevel.valueOf(response.urgency),
                    recommendedSpecialist = response.recommended_specialist,
                    lifestyleAdvice = response.lifestyle_advice,
                    confidenceScore = 0.9f, // Default or parse from summary
                    disclaimer = "Disclaimer: This is an AI-generated assessment, not a medical diagnosis."
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRiskPredictions(userData: String): Result<List<RiskPrediction>> {
        return try {
            val response = apiService.predictRisks(RiskRequestDto(userData))
            Result.success(response.map { dto ->
                RiskPrediction(
                    conditionName = dto.condition_name,
                    probability = dto.probability,
                    riskFactors = dto.risk_factors,
                    recommendations = dto.recommendations
                )
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
