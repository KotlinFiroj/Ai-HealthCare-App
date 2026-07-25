from app.core.agents.base_agent import BaseAgent
from app.core.tools import medical_tools

class AppointmentAgent(BaseAgent):
    """
    [AppointmentAgent]
    Specializes in finding doctors and scheduling consultations.
    """
    def __init__(self):
        system_prompt = """
        You are the Appointment specialist for MediAI Enterprise.
        Your goal is to help users find the right doctor and schedule appointments.
        Always use the 'search_doctors' tool to find matches and 'book_appointment' to schedule.
        Be professional and empathetic.
        """
        super().__init__(
            role="Appointment Assistant",
            system_prompt=system_prompt,
            tools=[medical_tools.search_doctors, medical_tools.book_appointment]
        )

class DiagnosticAgent(BaseAgent):
    """
    [DiagnosticAgent]
    Handles symptom checking and urgency assessment.
    """
    def __init__(self):
        system_prompt = """
        You are the Diagnostic Assistant for MediAI Enterprise.
        Your goal is to assess symptoms reported by users.
        You have access to 'get_patient_history' to understand context.
        If the symptoms sound life-threatening, use 'trigger_sos_alert' immediately.
        Always include a medical disclaimer.
        """
        super().__init__(
            role="Diagnostic Assistant",
            system_prompt=system_prompt,
            tools=[medical_tools.get_patient_history, medical_tools.trigger_sos_alert]
        )

class MediAiOrchestrator(BaseAgent):
    """
    [MediAiOrchestrator]
    The main routing agent that decides which specialist or tool to use.
    """
    def __init__(self):
        system_prompt = """
        You are the Primary Orchestrator for MediAI Enterprise.
        You receive user requests and decide how to handle them.
        You have several tools at your disposal:
        - search_doctors: For finding medical professionals.
        - book_appointment: For scheduling.
        - get_patient_history: For context on symptoms.
        - trigger_sos_alert: For emergencies.

        If a request is complex, decompose it and use the necessary tools.
        Always maintain a helpful, secure, and professional clinical tone.
        """
        super().__init__(
            role="MediAI Orchestrator",
            system_prompt=system_prompt,
            tools=[
                medical_tools.search_doctors,
                medical_tools.book_appointment,
                medical_tools.get_patient_history,
                medical_tools.trigger_sos_alert
            ]
        )
