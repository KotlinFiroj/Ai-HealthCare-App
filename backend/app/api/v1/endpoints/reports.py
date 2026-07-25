from typing import Any, List
from fastapi import APIRouter, Depends, HTTPException, UploadFile, File
from sqlalchemy.ext.asyncio import AsyncSession
from app.api import deps
from app.core.database import get_db
from app.models.user import User
from app.schemas.report import ReportResponse, ReportCreate
from app.services.report_service import ReportService
from app.services.patient_service import PatientService
from uuid import UUID

router = APIRouter()

@router.post("/", response_model=ReportResponse)
async def upload_report(
    *,
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user),
    report_in: ReportCreate
) -> Any:
    """
    Upload a medical report metadata and trigger AI analysis.
    """
    profile = await PatientService.get_profile(db, current_user.id)
    if not profile:
        raise HTTPException(status_code=400, detail="Patient profile must be created before uploading reports")

    return await ReportService.create_report(db, profile.id, report_in)

@router.get("/", response_model=List[ReportResponse])
async def get_my_reports(
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user)
) -> Any:
    """
    Get all medical reports for the current user.
    """
    profile = await PatientService.get_profile(db, current_user.id)
    if not profile:
        return []
    return await ReportService.get_user_reports(db, profile.id)

@router.get("/{id}", response_model=ReportResponse)
async def get_report_by_id(
    id: UUID,
    db: AsyncSession = Depends(get_db),
    current_user: User = Depends(deps.get_current_user)
) -> Any:
    """
    Get details of a specific medical report.
    """
    report = await ReportService.get_report(db, id)
    if not report:
        raise HTTPException(status_code=404, detail="Report not found")

    # Verify ownership
    profile = await PatientService.get_profile(db, current_user.id)
    if not profile or report.patient_id != profile.id:
        raise HTTPException(status_code=403, detail="Not authorized to view this report")

    return report
