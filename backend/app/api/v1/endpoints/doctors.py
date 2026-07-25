from typing import Any, List, Optional
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.ext.asyncio import AsyncSession
from app.api import deps
from app.core.database import get_db
from app.schemas.doctor import DoctorResponse
from app.services.doctor_service import DoctorService
from uuid import UUID

router = APIRouter()

@router.get("/", response_model=List[DoctorResponse])
async def search_doctors(
    db: AsyncSession = Depends(get_db),
    query: Optional[str] = None,
    specialization: Optional[str] = None
) -> Any:
    """
    Search and filter doctors.
    """
    return await DoctorService.search_doctors(db, query, specialization)

@router.get("/{id}", response_model=DoctorResponse)
async def get_doctor_by_id(
    id: UUID,
    db: AsyncSession = Depends(get_db)
) -> Any:
    """
    Get detailed information for a specific doctor.
    """
    doctor = await DoctorService.get_doctor(db, id)
    if not doctor:
        raise HTTPException(status_code=404, detail="Doctor not found")
    return doctor
