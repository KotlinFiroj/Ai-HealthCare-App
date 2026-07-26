# Tasks - Phase 33: Multi-Device Sync & Real-time Events

- `[x]` Implement Backend WebSocket Infrastructure
    - `[x]` Create `backend/app/core/websockets.py` (Connection Manager)
    - `[x]` Implement `WS /connect` endpoint in `backend/app/api/v1/endpoints/ws.py`
    - `[x]` Integrate Redis Pub/Sub for message broadcasting
- `[x]` Implement Android WebSocket Client (`:core:network`)
    - `[x]` Create `MediAIWebSocketClient.kt`
    - `[x]` Define `RealtimeEvent` models
- `[x]` Integrate Real-time Chat
    - `[x]` Update `ChatRepositoryImpl` to listen to WS events
    - `[x]` Update `ChatViewModel` for reactive updates
- `[x]` Integrate Real-time System Alerts
    - `[x]` Implement appointment status update broadcasting
- `[ ]` Verify Multi-Device Sync logic
