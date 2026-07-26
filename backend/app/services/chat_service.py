from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select
from app.models.chat import ChatMessage
from app.services.rag_service import rag_service
from app.core.agents.medical_agents import MediAiOrchestrator
from app.core.websockets import manager
from uuid import UUID
from typing import List

# Shared instance of the orchestrator
orchestrator = MediAiOrchestrator()

class ChatService:
    """
    [ChatService]
    Business logic for managing conversational AI workflows.
    Coordinates between RAG retrieval, Agent orchestration, and real-time broadcasting.
    """
    @staticmethod
    async def get_chat_history(db: AsyncSession, user_id: UUID) -> List[ChatMessage]:
        """
        Retrieves the chronological conversation history for a specific user.
        """
        result = await db.execute(
            select(ChatMessage)
            .where(ChatMessage.user_id == user_id)
            .order_by(ChatMessage.created_at.asc())
        )
        return list(result.scalars().all())

    @staticmethod
    async def send_message(db: AsyncSession, user_id: UUID, content: str) -> ChatMessage:
        """
        Orchestrates the full Agentic flow:
        1. Persists user query.
        2. Performs semantic retrieval via RAG.
        3. Invokes the AI Orchestrator for autonomous tool/agent selection.
        4. Broadcasts the response via WebSockets for real-time multi-device sync.
        """
        # 1. Save User Message
        user_msg = ChatMessage(user_id=user_id, role="user", content=content)
        db.add(user_msg)

        # 2. Retrieve Context (RAG) for the Orchestrator to see
        context = rag_service.retrieve_context(content)

        # 3. Augmented Request for Agent
        agent_query = f"""
        User Message: {content}
        Relevant Knowledge Context: {context}
        """

        # 4. Execute Agent Flow (Autonomous Tool Selection)
        assistant_content = await orchestrator.execute(agent_query)

        # 5. Save Assistant Message
        assistant_msg = ChatMessage(user_id=user_id, role="assistant", content=assistant_content)
        db.add(assistant_msg)

        await db.commit()
        await db.refresh(assistant_msg)

        # 6. Broadcast via WebSocket
        await manager.broadcast_event(
            "CHAT_MESSAGE",
            {"content": assistant_content, "role": "assistant"},
            str(user_id)
        )

        return assistant_msg
