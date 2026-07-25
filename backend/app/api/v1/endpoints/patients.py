from typing import Any
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.ext.asyncio import AsyncSession
from app.api import deps
from app.core.database import get_db
from app.models.user import User
from app.schemas.patient import PatientProfileResponse, PatientProfileCreate, PatientProfileUpdate
from app.services.patient_service import PatientService

router = APIRouter()

@router.get("/me/profile", response_model=PatientProfileResponse)
async def get_my_profile(
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user)
) -> Any:
    """
    Get the patient profile for the current user.
    """
    profile = await PatientService.get_profile(db, current_user.id)
    if not profile:
        raise HTTPException(status_code=404, detail="Patient profile not found")
    return profile

@router.post("/me/profile", response_model=PatientProfileResponse)
async def create_my_profile(
    *,
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user),
    profile_in: PatientProfileCreate
) -> Any:
    """
    Create a patient profile for the current user.
    """
    profile = await PatientService.get_profile(db, current_user.id)
    if profile:
        raise HTTPException(status_code=400, detail="Profile already exists")
    return await PatientService.create_profile(db, current_user.id, profile_in)

@router.put("/me/profile", response_model=PatientProfileResponse)
async def update_my_profile(
    *,
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user),
    profile_in: PatientProfileUpdate
) -> Any:
    """
    Update the patient profile for the current user.
    """
    profile = await PatientService.update_profile(db, current_user.id, profile_in)
    if not profile:
        raise HTTPException(status_code=404, detail="Patient profile not found")
    return profile
