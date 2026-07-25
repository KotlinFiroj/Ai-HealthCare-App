package com.mediai.enterprise.feature.chatbot.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequestDto(
    val content: String
)

@Serializable
data class ChatResponseDto(
    val id: String,
    val content: String,
    val role: String,
    val created_at: String
)
