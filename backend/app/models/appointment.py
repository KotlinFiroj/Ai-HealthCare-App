from sqlalchemy import Column, String, Float, Integer, ForeignKey, DateTime
from sqlalchemy.orm import relationship
from app.models.base import Base

class Doctor(Base):
    name = Column(String, nullable=False)
    specialization = Column(String, nullable=False)
    rating = Column(Float, default=0.0)
    reviews_count = Column(Integer, default=0)
    experience_years = Column(Integer)
    hospital_name = Column(String)
    about = Column(String)

    appointments = relationship("Appointment", back_populates="doctor")

class Appointment(Base):
    user_id = Column(ForeignKey("user.id"), nullable=False)
    doctor_id = Column(ForeignKey("doctor.id"), nullable=False)
    date_time = Column(DateTime, nullable=False)
    status = Column(String, default="UPCOMING") # UPCOMING, COMPLETED, CANCELLED
    type = Column(String, default="VIDEO") # VIDEO, IN_PERSON

    user = relationship("User", back_populates="appointments")
    doctor = relationship("Doctor", back_populates="appointments")
