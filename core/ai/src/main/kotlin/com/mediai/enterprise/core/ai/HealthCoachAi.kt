package com.mediai.enterprise.core.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [HealthCoachAi]
 * Uses Gemini 1.5 to provide personalized wellness plans and coaching.
 */
@Singleton
class HealthCoachAi @Inject constructor(
    private val generativeModel: GenerativeModel
) {
    private val coachPromptTemplate = """
        You are an expert AI Health Coach.
        Analyze the user's health profile and provide a personalized wellness plan in JSON format.

        Health Profile: {{PROFILE}}

        Fields to extract:
        - daily_goals (Array of Objects):
            - title (String)
            - description (String)
            - category (Enum: DIET, EXERCISE, SLEEP, STRESS)
        - nutritional_focus (String): 1-2 sentence recommendation.
        - exercise_routine (String): Recommended daily activity.
        - mental_wellbeing_tip (String): 1 tip for stress management.

        Safety Rules:
        - Focus on preventive wellness, not clinical treatment.
        - ALWAYS include this disclaimer: "Disclaimer: This is an AI-generated wellness plan. Consult your physician before starting any new diet or exercise regimen."

        Output ONLY valid JSON.
    """.trimIndent()

    suspend fun generateWellnessPlan(profile: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = generativeModel.generateContent(
                content {
                    text(coachPromptTemplate.replace("{{PROFILE}}", profile))
                }
            )
            Result.success(response.text ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
