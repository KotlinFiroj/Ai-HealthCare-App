from typing import Optional, List
from pydantic import BaseModel, UUID4
from datetime import datetime

class ReportBase(BaseModel):
    title: str
    category: Optional[str] = None
    date: Optional[datetime] = None

class ReportCreate(ReportBase):
    file_url: str

class ReportResponse(ReportBase):
    id: UUID4
    file_url: str
    ai_summary: Optional[str] = None

    class Config:
        from_attributes = True

class ReportStatus(BaseModel):
    id: UUID4
    status: str # PENDING, PROCESSING, COMPLETED, FAILED
