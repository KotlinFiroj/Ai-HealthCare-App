from sqlalchemy import Column, String, ForeignKey, DateTime, Text
from sqlalchemy.orm import relationship
from app.models.base import Base

class MedicalRecord(Base):
    patient_id = Column(ForeignKey("patientprofile.id"), nullable=False)
    title = Column(String, nullable=False)
    category = Column(String) # BLOOD_TEST, MRI, etc.
    date = Column(DateTime)
    file_url = Column(String)
    ocr_text = Column(Text)
    ai_summary = Column(Text)

    patient = relationship("PatientProfile", back_populates="medical_records")

class Medication(Base):
    patient_id = Column(ForeignKey("patientprofile.id"), nullable=False)
    name = Column(String, nullable=False)
    dosage = Column(String)
    frequency = Column(String)
    reminder_times = Column(String) # Comma-separated
    start_date = Column(DateTime)
    end_date = Column(DateTime)
