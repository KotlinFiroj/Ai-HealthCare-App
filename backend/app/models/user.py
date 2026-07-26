from sqlalchemy import Column, String, ForeignKey, Boolean
from sqlalchemy.orm import relationship
from app.models.base import Base

class User(Base):
    email = Column(String, unique=True, index=True, nullable=False)
    hashed_password = Column(String, nullable=False)
    full_name = Column(String)
    phone_number = Column(String)
    is_admin = Column(Boolean, default=False)
    fcm_token = Column(String, nullable=True)
    biometric_verified = Column(Boolean, default=False)
    last_biometric_auth = Column(DateTime, nullable=True)

    profile = relationship("PatientProfile", back_populates="user", uselist=False)
    appointments = relationship("Appointment", back_populates="user")
    chat_messages = relationship("ChatMessage", back_populates="user")

class PatientProfile(Base):
    user_id = Column(ForeignKey("user.id"), nullable=False)
    blood_group = Column(String)
    allergies = Column(String)
    chronic_conditions = Column(String)
    current_medications = Column(String)
    emergency_instructions = Column(String)

    user = relationship("User", back_populates="profile")
    medical_records = relationship("MedicalRecord", back_populates="patient")
