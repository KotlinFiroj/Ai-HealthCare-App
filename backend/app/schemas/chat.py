from typing import Optional, List
from pydantic import BaseModel, UUID4
from datetime import datetime

class ChatMessageBase(BaseModel):
    content: str

class ChatMessageCreate(ChatMessageBase):
    pass

class ChatMessageResponse(ChatMessageBase):
    id: UUID4
    role: str
    created_at: datetime

    class Config:
        from_attributes = True
