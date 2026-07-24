package com.mediai.enterprise.feature.chatbot.domain.repository

import com.mediai.enterprise.feature.chatbot.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChatHistory(): Flow<List<ChatMessage>>
    suspend fun sendMessage(content: String): Result<ChatMessage>
    suspend fun clearHistory()
}
