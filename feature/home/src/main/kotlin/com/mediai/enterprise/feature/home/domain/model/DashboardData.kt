package com.mediai.enterprise.feature.home.domain.model

data class DashboardData(
    val healthScore: Int,
    val metrics: List<HealthMetric>,
    val suggestions: List<AiSuggestion>,
    val upcomingAppointment: String? = null
)

data class HealthMetric(
    val type: MetricType,
    val value: String,
    val unit: String,
    val trend: Trend = Trend.STABLE
)

enum class MetricType {
    WATER, SLEEP, STEPS, HEART_RATE, BLOOD_PRESSURE, WEIGHT
}

enum class Trend {
    UP, DOWN, STABLE
}

data class AiSuggestion(
    val title: String,
    val description: String,
    val priority: Priority = Priority.MEDIUM
)

enum class Priority {
    LOW, MEDIUM, HIGH
}
