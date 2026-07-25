from typing import Optional
from pydantic import BaseModel, UUID4
from datetime import datetime

class AppointmentBase(BaseModel):
    doctor_id: UUID4
    date_time: datetime
    type: str = "VIDEO" # VIDEO, IN_PERSON

class AppointmentCreate(AppointmentBase):
    pass

class AppointmentUpdate(BaseModel):
    date_time: Optional[datetime] = None
    status: Optional[str] = None
    type: Optional[str] = None

class AppointmentResponse(AppointmentBase):
    id: UUID4
    user_id: UUID4
    status: str
    created_at: datetime

    class Config:
        from_attributes = True
