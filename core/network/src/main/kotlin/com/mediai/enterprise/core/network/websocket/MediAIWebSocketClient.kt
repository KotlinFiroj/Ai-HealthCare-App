package com.mediai.enterprise.core.network.websocket

import com.mediai.enterprise.core.security.TokenManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediAIWebSocketClient @Inject constructor(
    private val client: OkHttpClient,
    private val tokenManager: TokenManager
) {
    private var webSocket: WebSocket? = null
    private val _events = MutableSharedFlow<RealtimeEvent>()
    val events = _events.asSharedFlow()

    fun connect() {
        val token = tokenManager.getAccessToken() ?: return
        val request = Request.Builder()
            .url("ws://10.0.2.2/api/v1/ws/connect?token=$token")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                // Parse and emit event
                // In a real app, use kotlinx-serialization
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
            }
        })
    }

    fun disconnect() {
        webSocket?.close(1000, "User Disconnect")
    }
}
