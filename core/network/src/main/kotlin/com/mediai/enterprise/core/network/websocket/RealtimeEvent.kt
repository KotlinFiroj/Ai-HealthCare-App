package com.mediai.enterprise.core.network.websocket

import kotlinx.serialization.Serializable

@Serializable
data class RealtimeEvent(
    val type: String,
    val data: String // JSON string of the actual payload
)

@Serializable
data class ChatMessageEvent(
    val content: String,
    val role: String
)
