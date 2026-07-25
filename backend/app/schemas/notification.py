from typing import Optional, Dict
from pydantic import BaseModel, UUID4

class PushNotification(BaseModel):
    user_id: UUID4
    title: str
    body: str
    data: Optional[Dict[str, str]] = None

class NotificationLog(BaseModel):
    id: UUID4
    user_id: UUID4
    title: str
    status: str
    sent_at: str
