package com.mediai.enterprise.core.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthTimelineSummarizer @Inject constructor(
    private val generativeModel: GenerativeModel
) {
    private val promptTemplate = """
        You are an expert Medical AI assistant.
        Based on the following chronological list of health events (reports, appointments, medications),
        provide a concise and professional summary of the user's recent health journey.
        Highlight any patterns, completed treatments, or upcoming follow-ups.

        Events:
        {{EVENTS}}

        Summary (Plain English):
    """.trimIndent()

    suspend fun summarizeTimeline(events: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = generativeModel.generateContent(
                content {
                    text(promptTemplate.replace("{{EVENTS}}", events))
                }
            )
            Result.success(response.text ?: "No summary available.")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
