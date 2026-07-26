from typing import Dict, List
from fastapi import WebSocket
import json
import redis.asyncio as redis
from app.core.config import settings

class ConnectionManager:
    """
    [ConnectionManager]
    Manages active WebSocket connections and integrates with Redis Pub/Sub
    for multi-instance broadcasting.
    """
    def __init__(self):
        self.active_connections: Dict[str, List[WebSocket]] = {}
        self.redis_client = redis.from_url(settings.REDIS_URL)

    async def connect(self, user_id: str, websocket: WebSocket):
        await websocket.accept()
        if user_id not in self.active_connections:
            self.active_connections[user_id] = []
        self.active_connections[user_id].append(websocket)

    def disconnect(self, user_id: str, websocket: WebSocket):
        if user_id in self.active_connections:
            self.active_connections[user_id].remove(websocket)
            if not self.active_connections[user_id]:
                del self.active_connections[user_id]

    async def send_personal_message(self, message: dict, user_id: str):
        if user_id in self.active_connections:
            for connection in self.active_connections[user_id]:
                await connection.send_text(json.dumps(message))

    async def broadcast_event(self, event_type: str, data: dict, user_id: str):
        """
        Broadcasts an event to all user devices via Redis Pub/Sub.
        """
        payload = {
            "type": event_type,
            "data": data,
            "user_id": user_id
        }
        await self.redis_client.publish(f"user_events_{user_id}", json.dumps(payload))

    async def subscribe_to_user_events(self, user_id: str):
        """
        Listens to Redis for events and pushes them to active WebSockets.
        """
        pubsub = self.redis_client.pubsub()
        await pubsub.subscribe(f"user_events_{user_id}")

        try:
            async for message in pubsub.listen():
                if message["type"] == "message":
                    data = json.loads(message["data"])
                    await self.send_personal_message(data, user_id)
        finally:
            await pubsub.unsubscribe(f"user_events_{user_id}")

manager = ConnectionManager()
