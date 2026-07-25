package com.mediai.enterprise.core.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [MedicalDiagnosticsAi]
 * Uses Gemini 1.5 to perform symptom assessments and chronic disease risk predictions.
 */
@Singleton
class MedicalDiagnosticsAi @Inject constructor(
    private val generativeModel: GenerativeModel
) {
    private val symptomPromptTemplate = """
        You are an expert Medical Diagnostic Assistant.
        Analyze the following user-reported symptoms and provide a structured assessment in JSON format.

        Symptoms: {{SYMPTOMS}}

        Fields to extract:
        - possible_conditions (Array of Strings): List 2-3 potential medical conditions.
        - urgency (Enum: LOW, MEDIUM, HIGH, EMERGENCY): Determine how quickly the user should seek care.
        - recommended_specialist (String): e.g., "Cardiologist", "General Practitioner".
        - lifestyle_advice (String): 1-2 actionable tips.
        - confidence_score (Float): 0 to 1.

        Safety Rules:
        - If symptoms indicate an EMERGENCY (e.g., chest pain, difficulty breathing, sudden numbness), set urgency to 'EMERGENCY'.
        - ALWAYS include this disclaimer: "Disclaimer: This is an AI-generated assessment, not a medical diagnosis. Please consult a doctor immediately if you feel unwell."

        Output ONLY valid JSON.
    """.trimIndent()

    private val riskPromptTemplate = """
        You are an expert Medical Risk Prediction agent.
        Based on the user's reported lifestyle and health data, predict the risk levels for common chronic conditions.

        User Data: {{DATA}}

        Conditions to assess: Diabetes, Hypertension, Heart Disease, Kidney Disease.

        For each condition, provide:
        - condition_name (String)
        - probability (Float): 0 to 1.
        - risk_factors (Array of Strings): Why is this risk level assigned?
        - recommendations (Array of Strings): Specific preventive measures.

        Output ONLY a JSON array of objects.
    """.trimIndent()

    suspend fun assessSymptoms(symptoms: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = generativeModel.generateContent(
                content {
                    text(symptomPromptTemplate.replace("{{SYMPTOMS}}", symptoms))
                }
            )
            Result.success(response.text ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun predictRisks(userData: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = generativeModel.generateContent(
                content {
                    text(riskPromptTemplate.replace("{{DATA}}", userData))
                }
            )
            Result.success(response.text ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
