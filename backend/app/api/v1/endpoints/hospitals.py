from typing import Any, List
from fastapi import APIRouter, Depends
from sqlalchemy.ext.asyncio import AsyncSession
from app.core.database import get_db
from app.services.hospital_service import HospitalService
from pydantic import BaseModel

router = APIRouter()

class HospitalResponse(BaseModel):
    name: str
    address: str
    latitude: float
    longitude: float
    contact_number: str

@router.get("/nearby", response_model=List[HospitalResponse])
async def get_nearby(
    lat: float,
    lng: float,
    radius: float = 10.0,
    db: AsyncSession = Depends(get_db)
) -> Any:
    """
    Find medical facilities near a given coordinate.
    """
    return await HospitalService.get_nearby_hospitals(db, lat, lng, radius)
