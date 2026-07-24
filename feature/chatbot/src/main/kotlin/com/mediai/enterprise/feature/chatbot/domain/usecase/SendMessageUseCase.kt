package com.mediai.enterprise.feature.chatbot.domain.usecase

import com.mediai.enterprise.feature.chatbot.domain.model.ChatMessage
import com.mediai.enterprise.feature.chatbot.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(content: String): Result<ChatMessage> {
        if (content.isBlank()) return Result.failure(Exception("Message cannot be empty"))
        return repository.sendMessage(content)
    }
}
