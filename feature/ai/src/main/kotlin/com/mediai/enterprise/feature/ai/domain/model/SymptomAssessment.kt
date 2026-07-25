package com.mediai.enterprise.feature.ai.domain.model

data class SymptomAssessment(
    val possibleConditions: List<String>,
    val urgency: UrgencyLevel,
    val recommendedSpecialist: String,
    val lifestyleAdvice: String,
    val confidenceScore: Float,
    val disclaimer: String
)

enum class UrgencyLevel {
    LOW, MEDIUM, HIGH, EMERGENCY
}

data class RiskPrediction(
    val conditionName: String,
    val probability: Float,
    val riskFactors: List<String>,
    val recommendations: List<String>
)
