from typing import List, Optional
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select
from app.models.appointment import Appointment
from app.schemas.appointment import AppointmentCreate, AppointmentUpdate
from uuid import UUID

class AppointmentService:
    @staticmethod
    async def book_appointment(
        db: AsyncSession,
        user_id: UUID,
        appt_in: AppointmentCreate
    ) -> Appointment:
        db_obj = Appointment(
            user_id=user_id,
            doctor_id=appt_in.doctor_id,
            date_time=appt_in.date_time,
            type=appt_in.type
        )
        db.add(db_obj)
        await db.commit()
        await db.refresh(db_obj)
        return db_obj

    @staticmethod
    async def get_user_appointments(db: AsyncSession, user_id: UUID) -> List[Appointment]:
        result = await db.execute(
            select(Appointment)
            .where(Appointment.user_id == user_id)
            .order_by(Appointment.date_time.desc())
        )
        return list(result.scalars().all())

    @staticmethod
    async def update_appointment(
        db: AsyncSession,
        appt_id: UUID,
        appt_in: AppointmentUpdate
    ) -> Optional[Appointment]:
        result = await db.execute(select(Appointment).where(Appointment.id == appt_id))
        appt = result.scalars().first()
        if not appt:
            return None

        update_data = appt_in.model_dump(exclude_unset=True)
        for field, value in update_data.items():
            setattr(appt, field, value)

        db.add(appt)
        await db.commit()
        await db.refresh(appt)
        return appt

    @staticmethod
    async def cancel_appointment(db: AsyncSession, appt_id: UUID) -> bool:
        result = await db.execute(select(Appointment).where(Appointment.id == appt_id))
        appt = result.scalars().first()
        if not appt:
            return False

        appt.status = "CANCELLED"
        await db.commit()
        return True
