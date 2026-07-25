from typing import List, Optional
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select, or_
from app.models.appointment import Doctor
from uuid import UUID

class DoctorService:
    @staticmethod
    async def search_doctors(
        db: AsyncSession,
        query: Optional[str] = None,
        specialization: Optional[str] = None
    ) -> List[Doctor]:
        statement = select(Doctor)
        if specialization:
            statement = statement.where(Doctor.specialization == specialization)
        if query:
            statement = statement.where(
                or_(
                    Doctor.name.ilike(f"%{query}%"),
                    Doctor.about.ilike(f"%{query}%")
                )
            )
        result = await db.execute(statement)
        return list(result.scalars().all())

    @staticmethod
    async def get_doctor(db: AsyncSession, doctor_id: UUID) -> Optional[Doctor]:
        result = await db.execute(select(Doctor).where(Doctor.id == doctor_id))
        return result.scalars().first()
