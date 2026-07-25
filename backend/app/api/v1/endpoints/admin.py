from typing import Any, List
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.ext.asyncio import AsyncSession
from app.api import deps
from app.core.database import get_db
from app.models.user import User
from app.schemas.doctor import DoctorCreate, DoctorResponse
from app.services.doctor_service import DoctorService
from app.services.rag_service import rag_service
from pydantic import BaseModel

router = APIRouter()

class KnowledgeIn(BaseModel):
    text: str
    source: str

def verify_admin(current_user: User = Depends(deps.get_current_user)):
    if not current_user.is_admin:
        raise HTTPException(status_code=403, detail="The user does not have enough privileges")

@router.post("/doctors", response_model=DoctorResponse)
async def create_doctor(
    *,
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(verify_admin),
    doctor_in: DoctorCreate
) -> Any:
    """
    Create a new doctor (Admin only).
    """
    # This would call a method in doctor_service to create a new doctor record
    pass

@router.post("/knowledge")
async def add_knowledge(
    *,
    current_user: User = Depends(verify_admin),
    knowledge_in: KnowledgeIn
) -> Any:
    """
    Add a new snippet to the RAG knowledge base (Admin only).
    """
    await rag_service.embed_and_store([knowledge_in.text], [{"source": knowledge_in.source}])
    return {"status": "success", "message": "Knowledge added to vector store"}
