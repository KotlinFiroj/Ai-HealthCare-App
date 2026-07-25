from typing import Any, List
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.ext.asyncio import AsyncSession
from app.api import deps
from app.core.database import get_db
from app.models.user import User
from app.schemas.analytics import HealthSummary, TrendData
from app.services.analytics_service import AnalyticsService
from app.services.patient_service import PatientService

router = APIRouter()

@router.get("/stats", response_model=HealthSummary)
async def get_health_stats(
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user)
) -> Any:
    """
    Get aggregated health statistics for the current user.
    """
    profile = await PatientService.get_profile(db, current_user.id)
    if not profile:
        raise HTTPException(status_code=404, detail="Patient profile not found")
    return await AnalyticsService.get_health_summary(db, profile.id)

@router.get("/trends", response_model=List[TrendData])
async def get_health_trends(
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user)
) -> Any:
    """
    Get historical health trends for the current user.
    """
    profile = await PatientService.get_profile(db, current_user.id)
    if not profile:
        raise HTTPException(status_code=404, detail="Patient profile not found")
    return await AnalyticsService.get_vitals_trends(db, profile.id)
