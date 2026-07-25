package com.mediai.enterprise.feature.ai.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class SymptomRequestDto(
    val symptoms: String
)

@Serializable
data class SymptomResponseDto(
    val possible_conditions: List<String> = emptyList(),
    val urgency: String = "LOW",
    val recommended_specialist: String = "General Practitioner",
    val lifestyle_advice: String = "",
    val summary: String? = null
)

@Serializable
data class RiskRequestDto(
    val user_data: String
)

@Serializable
data class RiskResponseDto(
    val condition_name: String,
    val probability: Float,
    val risk_factors: List<String>,
    val recommendations: List<String>
)
