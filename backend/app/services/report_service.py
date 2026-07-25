from typing import Optional, List
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select
from app.models.record import MedicalRecord
from app.schemas.report import ReportCreate
from app.tasks.medical_tasks import process_medical_report_task
from uuid import UUID

class ReportService:
    @staticmethod
    async def create_report(db: AsyncSession, patient_id: UUID, report_in: ReportCreate) -> MedicalRecord:
        db_obj = MedicalRecord(
            patient_id=patient_id,
            **report_in.model_dump()
        )
        db.add(db_obj)
        await db.commit()
        await db.refresh(db_obj)

        # Trigger background processing
        process_medical_report_task.delay(str(db_obj.id))

        return db_obj

    @staticmethod
    async def get_user_reports(db: AsyncSession, patient_id: UUID) -> List[MedicalRecord]:
        result = await db.execute(
            select(MedicalRecord)
            .where(MedicalRecord.patient_id == patient_id)
            .order_by(MedicalRecord.date.desc())
        )
        return list(result.scalars().all())

    @staticmethod
    async def get_report(db: AsyncSession, report_id: UUID) -> Optional[MedicalRecord]:
        result = await db.execute(select(MedicalRecord).where(MedicalRecord.id == report_id))
        return result.scalars().first()
