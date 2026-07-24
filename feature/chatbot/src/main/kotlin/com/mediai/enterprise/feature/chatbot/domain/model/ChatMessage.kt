package com.mediai.enterprise.feature.chatbot.domain.model

data class ChatMessage(
    val id: Long = 0,
    val content: String,
    val role: ChatRole,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ChatRole {
    USER, ASSISTANT
}
