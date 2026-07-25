from typing import Optional
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select, update
from app.models.user import PatientProfile
from app.schemas.patient import PatientProfileCreate, PatientProfileUpdate
from uuid import UUID

class PatientService:
    @staticmethod
    async def get_profile(db: AsyncSession, user_id: UUID) -> Optional[PatientProfile]:
        result = await db.execute(select(PatientProfile).where(PatientProfile.user_id == user_id))
        return result.scalars().first()

    @staticmethod
    async def create_profile(db: AsyncSession, user_id: UUID, profile_in: PatientProfileCreate) -> PatientProfile:
        db_obj = PatientProfile(
            user_id=user_id,
            **profile_in.model_dump()
        )
        db.add(db_obj)
        await db.commit()
        await db.refresh(db_obj)
        return db_obj

    @staticmethod
    async def update_profile(db: AsyncSession, user_id: UUID, profile_in: PatientProfileUpdate) -> Optional[PatientProfile]:
        profile = await PatientService.get_profile(db, user_id)
        if not profile:
            return None

        update_data = profile_in.model_dump(exclude_unset=True)
        for field, value in update_data.items():
            setattr(profile, field, value)

        db.add(profile)
        await db.commit()
        await db.refresh(profile)
        return profile
