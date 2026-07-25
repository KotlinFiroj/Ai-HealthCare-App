package com.mediai.enterprise.feature.home.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class HealthSummaryDto(
    val health_score: Int,
    val summary_text: String,
    val risk_level: String
)

@Serializable
data class TrendDataDto(
    val metric_name: String,
    val points: List<TrendPointDto>
)

@Serializable
data class TrendPointDto(
    val label: String,
    val value: Float
)
