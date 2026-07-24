package com.mediai.enterprise.feature.chatbot.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediai.enterprise.feature.chatbot.domain.model.ChatMessage
import com.mediai.enterprise.feature.chatbot.domain.repository.ChatRepository
import com.mediai.enterprise.feature.chatbot.domain.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMessages()
    }

    private fun loadMessages() {
        repository.getChatHistory()
            .onEach { messages ->
                _uiState.update { it.copy(messages = messages) }
            }
            .launchIn(viewModelScope)
    }

    fun sendMessage(content: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isTyping = true) }
            val result = sendMessageUseCase(content)
            if (result.isFailure) {
                // Handle error (e.g. show toast)
            }
            _uiState.update { it.copy(isTyping = false) }
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isTyping: Boolean = false,
    val error: String? = null
)
