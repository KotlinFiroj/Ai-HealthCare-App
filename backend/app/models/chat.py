from sqlalchemy import Column, String, ForeignKey, Text, DateTime
from sqlalchemy.orm import relationship
from datetime import datetime
from app.models.base import Base

class ChatMessage(Base):
    user_id = Column(ForeignKey("user.id"), nullable=False)
    role = Column(String, nullable=False) # "user" or "assistant"
    content = Column(Text, nullable=False)

    user = relationship("User", back_populates="chat_messages")

# Note: Need to update User model to add chat_messages relationship
