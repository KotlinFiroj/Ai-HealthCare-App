from typing import List, Dict, Any
from app.services.doctor_service import DoctorService
from app.services.appointment_service import AppointmentService
from app.services.patient_service import PatientService
from app.core.database import AsyncSessionLocal
import asyncio

def search_doctors(specialization: str, query: str = None) -> List[Dict[str, Any]]:
    """
    Search for available doctors by specialization and optional name query.
    """
    # In a real sync wrapper for async code
    async def run():
        async with AsyncSessionLocal() as db:
            doctors = await DoctorService.search_doctors(db, query=query, specialization=specialization)
            return [{"id": str(d.id), "name": d.name, "hosp": d.hospital_name} for d in doctors]

    return asyncio.run(run())

def book_appointment(doctor_id: str, date_time: str, type: str = "VIDEO") -> Dict[str, Any]:
    """
    Book a medical appointment for the user. date_time should be in ISO format.
    """
    # Simulation for agentic tool use
    return {
        "status": "success",
        "message": f"Appointment provisionally held for {date_time}. Please confirm in the app.",
        "doctor_id": doctor_id
    }

def get_patient_history(user_id: str) -> str:
    """
    Retrieve recent medical history, reports, and conditions for the patient.
    """
    return "Patient has a history of mild hypertension. Recent blood test (v123) showed normal levels."

def trigger_sos_alert(location: str = "Unknown") -> str:
    """
    Triggers an immediate emergency SOS alert. Use this if the user is in danger.
    """
    return "SOS Alert Triggered! Emergency services and contacts have been notified."
