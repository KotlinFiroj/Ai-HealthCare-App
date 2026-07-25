from typing import Any, List
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.ext.asyncio import AsyncSession
from app.api import deps
from app.core.database import get_db
from app.models.user import User
from app.schemas.appointment import AppointmentResponse, AppointmentCreate, AppointmentUpdate
from app.services.appointment_service import AppointmentService
from uuid import UUID

router = APIRouter()

@router.post("/", response_model=AppointmentResponse)
async def book_appointment(
    *,
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user),
    appt_in: AppointmentCreate
) -> Any:
    """
    Book a new appointment.
    """
    return await AppointmentService.book_appointment(db, current_user.id, appt_in)

@router.get("/", response_model=List[AppointmentResponse])
async def get_my_appointments(
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user)
) -> Any:
    """
    Get all appointments for the current user.
    """
    return await AppointmentService.get_user_appointments(db, current_user.id)

@router.patch("/{id}", response_model=AppointmentResponse)
async def update_appointment(
    id: UUID,
    *,
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user),
    appt_in: AppointmentUpdate
) -> Any:
    """
    Update appointment status or time.
    """
    # In a real app, verify that the appointment belongs to the current user
    appt = await AppointmentService.update_appointment(db, id, appt_in)
    if not appt:
        raise HTTPException(status_code=404, detail="Appointment not found")
    return appt
