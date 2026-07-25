package com.mediai.enterprise.feature.chatbot.data.repository

import com.mediai.enterprise.core.database.dao.ChatDao
import com.mediai.enterprise.core.database.entity.ChatMessageEntity
import com.mediai.enterprise.feature.chatbot.data.remote.ChatApiService
import com.mediai.enterprise.feature.chatbot.data.remote.model.ChatRequestDto
import com.mediai.enterprise.feature.chatbot.domain.model.ChatMessage
import com.mediai.enterprise.feature.chatbot.domain.model.ChatRole
import com.mediai.enterprise.feature.chatbot.domain.repository.ChatRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDao: ChatDao,
    private val apiService: ChatApiService
) : ChatRepository {

    override fun getChatHistory(): Flow<List<ChatMessage>> {
        // Source of truth is the local database for UI responsiveness
        return chatDao.getAllMessages().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun sendMessage(content: String): Result<ChatMessage> {
        return try {
            // 1. Save User Message locally
            val userMsg = ChatMessage(content = content, role = ChatRole.USER)
            chatDao.insertMessage(userMsg.toEntity())

            // 2. Call Backend AI
            val response = apiService.sendMessage(ChatRequestDto(content))

            // 3. Save Assistant Message locally
            val assistantMsg = ChatMessage(
                content = response.content,
                role = if (response.role == "user") ChatRole.USER else ChatRole.ASSISTANT,
                timestamp = System.currentTimeMillis() // Or parse from backend
            )
            chatDao.insertMessage(assistantMsg.toEntity())

            Result.success(assistantMsg)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearHistory() {
        chatDao.clearHistory()
    }

    private fun ChatMessageEntity.toDomain() = ChatMessage(
        id = id,
        content = content,
        role = if (role == "user") ChatRole.USER else ChatRole.ASSISTANT,
        timestamp = timestamp
    )

    private fun ChatMessage.toEntity() = ChatMessageEntity(
        content = content,
        role = if (role == ChatRole.USER) "user" else "assistant",
        timestamp = timestamp
    )
}
