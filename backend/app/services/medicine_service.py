from typing import List, Optional
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select
from app.models.record import Medication
from app.schemas.patient import PatientProfileBase
from uuid import UUID

class MedicineService:
    @staticmethod
    async def get_medications(db: AsyncSession, patient_id: UUID) -> List[Medication]:
        result = await db.execute(select(Medication).where(Medication.patient_id == patient_id))
        return list(result.scalars().all())

    @staticmethod
    async def log_adherence(db: AsyncSession, medication_id: UUID, status: str):
        # Implementation for logging whether a dose was taken or missed
        pass
