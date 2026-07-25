package com.mediai.enterprise.feature.chatbot.data.remote

import com.mediai.enterprise.feature.chatbot.data.remote.model.ChatRequestDto
import com.mediai.enterprise.feature.chatbot.data.remote.model.ChatResponseDto
import retrofit2.http.*

interface ChatApiService {
    @POST("chat/")
    suspend fun sendMessage(@Body request: ChatRequestDto): ChatResponseDto

    @GET("chat/history")
    suspend fun getHistory(): List<ChatResponseDto>
}
