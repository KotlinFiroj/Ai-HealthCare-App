package com.mediai.enterprise.feature.analytics.domain.model

data class WellnessPlan(
    val dailyGoals: List<DailyGoal>,
    val nutritionalFocus: String,
    val exerciseRoutine: String,
    val mentalWellbeingTip: String,
    val disclaimer: String
)

data class DailyGoal(
    val title: String,
    val description: String,
    val category: GoalCategory,
    val isCompleted: Boolean = false
)

enum class GoalCategory {
    DIET, EXERCISE, SLEEP, STRESS
}

data class HealthTrend(
    val metricName: String,
    val dataPoints: List<TrendPoint>
)

data class TrendPoint(
    val date: String,
    val value: Float
)
