from typing import Optional
from pydantic import BaseModel, UUID4
from datetime import datetime

class DoctorBase(BaseModel):
    name: str
    specialization: str
    rating: float = 0.0
    reviews_count: int = 0
    experience_years: Optional[int] = None
    hospital_name: Optional[str] = None
    about: Optional[str] = None

class DoctorCreate(DoctorBase):
    pass

class DoctorResponse(DoctorBase):
    id: UUID4
    created_at: datetime

    class Config:
        from_attributes = True
