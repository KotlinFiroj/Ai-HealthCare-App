package com.mediai.enterprise.feature.analytics.data.repository

import com.mediai.enterprise.core.ai.HealthCoachAi
import com.mediai.enterprise.feature.analytics.domain.model.*
import com.mediai.enterprise.feature.analytics.domain.repository.AnalyticsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor(
    private val healthCoachAi: HealthCoachAi
) : AnalyticsRepository {

    override suspend fun getWellnessPlan(profile: String): Result<WellnessPlan> {
        delay(1500)
        // Mocking the result of healthCoachAi.generateWellnessPlan(profile)
        return Result.success(
            WellnessPlan(
                dailyGoals = listOf(
                    DailyGoal("Morning Walk", "30 minutes of brisk walking", GoalCategory.EXERCISE),
                    DailyGoal("Hydration", "Drink 2L of water", GoalCategory.DIET),
                    DailyGoal("Sleep Hygiene", "No screen 1 hour before bed", GoalCategory.SLEEP)
                ),
                nutritionalFocus = "Increase fiber intake and reduce processed sugars.",
                exerciseRoutine = "Moderate cardio for 30 mins, 5 days a week.",
                mentalWellbeingTip = "Practice deep breathing for 5 mins during breaks.",
                disclaimer = "Disclaimer: This is an AI-generated wellness plan. Consult your physician before starting any new diet or exercise regimen."
            )
        )
    }

    override fun getHealthTrends(): Flow<List<HealthTrend>> = flowOf(
        listOf(
            HealthTrend("Steps", listOf(
                TrendPoint("Mon", 4500f), TrendPoint("Tue", 8200f),
                TrendPoint("Wed", 12000f), TrendPoint("Thu", 7500f),
                TrendPoint("Fri", 10000f), TrendPoint("Sat", 15000f),
                TrendPoint("Sun", 9000f)
            )),
            HealthTrend("Weight", listOf(
                TrendPoint("Week 1", 82f), TrendPoint("Week 2", 81.5f),
                TrendPoint("Week 3", 81.2f), TrendPoint("Week 4", 80.5f)
            ))
        )
    )
}
