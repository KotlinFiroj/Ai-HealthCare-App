from app.core.celery_app import celery_app
from app.core.ai_engine import ai_engine
from app.core.database import AsyncSessionLocal
from app.models.record import MedicalRecord
from sqlalchemy import select, update
import asyncio
from uuid import UUID

@celery_app.task(name="app.tasks.medical_tasks.process_medical_report")
def process_medical_report_task(report_id: str):
    async def run():
        async with AsyncSessionLocal() as db:
            # 1. Fetch report
            result = await db.execute(select(MedicalRecord).where(MedicalRecord.id == UUID(report_id)))
            report = result.scalars().first()
            if not report:
                return "Report not found"

            # 2. Extract OCR (Simulated for now, would use Tesseract/Vision API)
            ocr_text = report.ocr_text or "Simulated OCR text content from image."

            # 3. Analyze with AI
            analysis = await ai_engine.analyze_medical_text(ocr_text, report.category or "General")

            # 4. Update DB
            report.ai_summary = analysis.get("summary")
            # In a real app, we'd have a separate table for detailed analysis

            await db.commit()
            return f"Processed report {report_id}"

    return asyncio.run(run())
