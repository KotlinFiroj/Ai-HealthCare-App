package com.mediai.enterprise.feature.chatbot.data.knowledge

import javax.inject.Inject
import javax.inject.Singleton

/**
 * [MedicalKnowledgeProvider]
 * Provides relevant medical context and hospital policies for RAG (Retrieval-Augmented Generation).
 */
@Singleton
class MedicalKnowledgeProvider @Inject constructor() {

    private val knowledgeBase = mapOf(
        "visiting hours" to "Hospital visiting hours are from 9:00 AM to 8:00 PM daily. Please ensure only two visitors per patient at a time.",
        "diabetes" to "Diabetes is a chronic condition that affects how your body turns food into energy. Common symptoms include increased thirst, frequent urination, and unexplained weight loss.",
        "appointment" to "Appointments can be booked via the app or by calling our helpdesk at 1-800-MED-IAI. Please arrive 15 minutes prior to your slot.",
        "emergency" to "In case of a life-threatening emergency, please use the SOS button in the app or call 911 (or your local emergency number) immediately.",
        "fasting" to "For most blood tests, a fasting period of 8 to 12 hours is required. You may drink water but should avoid other beverages and food.",
        "mri" to "An MRI scan is a painless procedure that uses strong magnetic fields and radio waves to create detailed images of the organs and tissues within the body."
    )

    /**
     * Retrieves relevant context based on keywords in the user query.
     */
    fun getRelevantContext(query: String): String {
        val lowercaseQuery = query.lowercase()
        return knowledgeBase.filter { (key, _) ->
            lowercaseQuery.contains(key)
        }.values.joinToString("\n\n")
    }
}
