from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select
from app.models.chat import ChatMessage
from app.services.rag_service import rag_service
from app.core.ai_engine import ai_engine
from uuid import UUID
from typing import List

class ChatService:
    @staticmethod
    async def get_chat_history(db: AsyncSession, user_id: UUID) -> List[ChatMessage]:
        result = await db.execute(
            select(ChatMessage)
            .where(ChatMessage.user_id == user_id)
            .order_by(ChatMessage.created_at.asc())
        )
        return list(result.scalars().all())

    @staticmethod
    async def send_message(db: AsyncSession, user_id: UUID, content: str) -> ChatMessage:
        # 1. Save User Message
        user_msg = ChatMessage(user_id=user_id, role="user", content=content)
        db.add(user_msg)

        # 2. Retrieve Context (RAG)
        context = rag_service.retrieve_context(content)

        # 3. Augment Prompt & Generate Response
        # We reuse the analyze_medical_text logic but adapted for chat
        augmented_prompt = f"""
        You are an expert MediAI Assistant.
        Use the following retrieved context to answer the user's question accurately.
        If the answer isn't in the context, use your general knowledge but mention it's not from the official database.

        Context:
        {context}

        User Question: {content}
        """

        # Call AI Engine
        response = await ai_engine.analyze_medical_text(augmented_prompt, "Chat")
        assistant_content = response.get("summary", "I'm sorry, I couldn't process your request.")

        # 4. Save Assistant Message
        assistant_msg = ChatMessage(user_id=user_id, role="assistant", content=assistant_content)
        db.add(assistant_msg)

        await db.commit()
        await db.refresh(assistant_msg)
        return assistant_msg
