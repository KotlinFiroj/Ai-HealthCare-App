import google.generativeai as genai
from app.core.config import settings
import json
from typing import Optional

class AiEngine:
    def __init__(self):
        if settings.GEMINI_API_KEY:
            genai.configure(api_key=settings.GEMINI_API_KEY)
        self.model = genai.GenerativeModel('gemini-1.5-flash')

    async def analyze_medical_text(self, text: str, category: str) -> dict:
        prompt = f"""
        You are an expert Medical AI Assistant.
        Analyze the following text from a {category} medical report and provide a structured summary.

        Text: {text}

        Provide the result strictly in JSON format with these fields:
        - summary: A plain English summary for the patient.
        - key_findings: List of important medical findings.
        - risk_level: LOW, MEDIUM, or HIGH.
        - suggested_questions: 3 questions for the doctor.
        """

        try:
            response = self.model.generate_content(prompt)
            # Find the first valid JSON block in the response
            content = response.text
            start = content.find('{')
            end = content.rfind('}') + 1
            return json.loads(content[start:end])
        except Exception as e:
            return {
                "summary": "Error processing report analysis.",
                "error": str(e)
            }

ai_engine = AiEngine()
