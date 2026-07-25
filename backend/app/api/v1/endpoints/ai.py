from typing import Any
from fastapi import APIRouter, Depends, HTTPException
from app.api import deps
from app.core.ai_engine import ai_engine
from app.core.ai_engine import AiEngine
from pydantic import BaseModel

router = APIRouter()

class SymptomRequest(BaseModel):
    symptoms: str

class RiskRequest(BaseModel):
    user_data: str

@router.post("/symptoms")
async def assess_symptoms(
    request: SymptomRequest,
    current_user: Any = Depends(deps.get_current_user)
) -> Any:
    """
    Perform AI-driven symptom assessment.
    """
    # Reuse ai_engine or a specialized diagnostics engine
    return await ai_engine.analyze_medical_text(request.symptoms, "Symptom Checker")

@router.post("/risks")
async def predict_risks(
    request: RiskRequest,
    current_user: Any = Depends(deps.get_current_user)
) -> Any:
    """
    Predict chronic disease risks based on user data.
    """
    return await ai_engine.analyze_medical_text(request.user_data, "Risk Prediction")
