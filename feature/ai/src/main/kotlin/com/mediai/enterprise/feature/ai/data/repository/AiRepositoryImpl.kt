package com.mediai.enterprise.feature.ai.data.repository

import com.mediai.enterprise.core.ai.MedicalDiagnosticsAi
import com.mediai.enterprise.feature.ai.domain.model.*
import com.mediai.enterprise.feature.ai.domain.repository.AiRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val diagnosticsAi: MedicalDiagnosticsAi
) : AiRepository {

    override suspend fun assessSymptoms(symptoms: String): Result<SymptomAssessment> {
        // In a real implementation, we would parse the JSON from diagnosticsAi.assessSymptoms(symptoms)
        // For the sake of the demonstration/mocking flow, we'll return a structured mock
        delay(1500)

        return if (symptoms.lowercase().contains("chest pain")) {
            Result.success(
                SymptomAssessment(
                    possibleConditions = listOf("Angina", "Myocardial Infarction"),
                    urgency = UrgencyLevel.EMERGENCY,
                    recommendedSpecialist = "Emergency Cardiology",
                    lifestyleAdvice = "Stop all activity immediately and call emergency services.",
                    confidenceScore = 0.95f,
                    disclaimer = "Disclaimer: This is an AI-generated assessment, not a medical diagnosis."
                )
            )
        } else {
            Result.success(
                SymptomAssessment(
                    possibleConditions = listOf("Common Cold", "Influenza"),
                    urgency = UrgencyLevel.LOW,
                    recommendedSpecialist = "General Practitioner",
                    lifestyleAdvice = "Stay hydrated and get plenty of rest.",
                    confidenceScore = 0.88f,
                    disclaimer = "Disclaimer: This is an AI-generated assessment, not a medical diagnosis."
                )
            )
        }
    }

    override suspend fun getRiskPredictions(userData: String): Result<List<RiskPrediction>> {
        delay(1500)
        return Result.success(
            listOf(
                RiskPrediction("Diabetes", 0.15f, listOf("Active lifestyle", "Low sugar intake"), listOf("Maintain current diet")),
                RiskPrediction("Hypertension", 0.45f, listOf("High sodium intake", "Sedentary work"), listOf("Reduce salt", "Daily walk")),
                RiskPrediction("Heart Disease", 0.08f, listOf("Non-smoker", "Regular exercise"), listOf("Keep it up")),
                RiskPrediction("Kidney Disease", 0.12f, listOf("Adequate hydration"), listOf("Drink 2L water daily"))
            )
        )
    }
}
