from typing import Any, List
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.ext.asyncio import AsyncSession
from app.api import deps
from app.core.database import get_db
from app.models.user import User
from app.schemas.chat import ChatMessageResponse, ChatMessageCreate
from app.services.chat_service import ChatService

router = APIRouter()

@router.post("/", response_model=ChatMessageResponse)
async def send_message(
    *,
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user),
    message_in: ChatMessageCreate
) -> Any:
    """
    Send a message to the AI assistant and get a grounded response.
    """
    return await ChatService.send_message(db, current_user.id, message_in.content)

@router.get("/history", response_model=List[ChatMessageResponse])
async def get_history(
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user)
) -> Any:
    """
    Retrieve the conversation history for the current user.
    """
    return await ChatService.get_chat_history(db, current_user.id)
