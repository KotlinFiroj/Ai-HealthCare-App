package com.mediai.enterprise.core.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [MedicalReportSummarizer]
 * Uses Gemini 1.5 Flash to provide patient-friendly summaries of medical reports.
 */
@Singleton
class MedicalReportSummarizer @Inject constructor(
    private val generativeModel: GenerativeModel
) {
    private val promptTemplate = """
        You are an expert Medical AI Assistant.
        Analyze the following text extracted from a medical report (Category: {{CATEGORY}}) and provide a detailed analysis in JSON format.

        Fields to extract:
        - summary (String): A 2-3 sentence patient-friendly explanation in plain English.
        - risk_factors (Array of Strings): Highlight any values outside normal range or suspicious findings.
        - suggested_questions (Array of Strings): 3-5 questions the patient should ask their doctor.
        - confidence_score (Float): 0 to 1 representing your confidence in the analysis.

        Safety Rules:
        - ALWAYS include this disclaimer in 'summary': "Disclaimer: This AI-generated summary is for informational purposes and not a substitute for professional medical advice."
        - Use simple language (no jargon where possible).
        - If the report is empty or illegible, return an appropriate error message in the 'summary'.

        Raw Text:
        {{TEXT}}

        Output ONLY valid JSON.
    """.trimIndent()

    suspend fun summarizeReport(rawText: String, category: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = generativeModel.generateContent(
                content {
                    text(promptTemplate
                        .replace("{{TEXT}}", rawText)
                        .replace("{{CATEGORY}}", category)
                    )
                }
            )
            Result.success(response.text ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
