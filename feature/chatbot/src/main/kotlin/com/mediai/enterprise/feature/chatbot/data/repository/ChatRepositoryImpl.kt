package com.mediai.enterprise.feature.chatbot.data.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.mediai.enterprise.core.database.dao.ChatDao
import com.mediai.enterprise.core.database.entity.ChatMessageEntity
import com.mediai.enterprise.feature.chatbot.data.knowledge.MedicalKnowledgeProvider
import com.mediai.enterprise.feature.chatbot.domain.model.ChatMessage
import com.mediai.enterprise.feature.chatbot.domain.model.ChatRole
import com.mediai.enterprise.feature.chatbot.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDao: ChatDao,
    private val knowledgeProvider: MedicalKnowledgeProvider,
    private val generativeModel: GenerativeModel
) : ChatRepository {

    override fun getChatHistory(): Flow<List<ChatMessage>> {
        return chatDao.getAllMessages().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun sendMessage(content: String): Result<ChatMessage> {
        return try {
            // 1. Save User Message
            val userMsg = ChatMessage(content = content, role = ChatRole.USER)
            chatDao.insertMessage(userMsg.toEntity())

            // 2. Retrieve Context (RAG)
            val context = knowledgeProvider.getRelevantContext(content)

            // 3. Prepare AI Prompt
            val systemPrompt = """
                You are a highly capable Medical Assistant for MediAI Enterprise.
                Use the following knowledge snippets to answer the user's question.
                If the information is not in the knowledge snippets, answer based on your general medical knowledge,
                but always prioritize the provided snippets.

                Safety Rules:
                - Always include a medical disclaimer: "Disclaimer: This information is for educational purposes and not a substitute for professional medical advice."
                - Do not give specific drug prescriptions or dosages.
                - If the situation sounds like an emergency, tell them to use the SOS button immediately.

                Knowledge Snippets:
                $context
            """.trimIndent()

            // 4. Call Gemini
            val response = generativeModel.generateContent(
                content {
                    text("$systemPrompt\n\nUser Question: $content")
                }
            )

            val assistantContent = response.text ?: "I'm sorry, I couldn't process that request."
            val assistantMsg = ChatMessage(content = assistantContent, role = ChatRole.ASSISTANT)

            // 5. Save Assistant Message
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
