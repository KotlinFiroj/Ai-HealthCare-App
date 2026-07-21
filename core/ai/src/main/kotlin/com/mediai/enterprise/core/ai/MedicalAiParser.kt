package com.mediai.enterprise.core.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [MedicalAiParser]
 * Uses Gemini 2.5 (Flash/Pro) to parse raw OCR text into structured medical data.
 */
@Singleton
class MedicalAiParser @Inject constructor(
    private val generativeModel: GenerativeModel
) {
    private val promptTemplate = """
        You are an expert Medical AI assistant.
        Analyze the following text extracted from a medical prescription or report and extract structured information in JSON format.

        Fields to extract:
        - doctor_name (String)
        - hospital_name (String)
        - date (String)
        - medicines (Array of Objects):
            - name (String)
            - dosage (String)
            - frequency (String)
            - duration (String)
            - notes (String)

        If any field is not found, use null.
        Output ONLY the valid JSON.

        Raw Text:
        {{TEXT}}
    """.trimIndent()

    suspend fun parsePrescription(rawText: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = generativeModel.generateContent(
                content {
                    text(promptTemplate.replace("{{TEXT}}", rawText))
                }
            )
            Result.success(response.text ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
