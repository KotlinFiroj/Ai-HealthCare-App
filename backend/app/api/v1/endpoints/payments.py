from typing import Any
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.ext.asyncio import AsyncSession
from app.api import deps
from app.core.database import get_db
from app.services.payment_service import PaymentService
from pydantic import BaseModel
from uuid import UUID

router = APIRouter()

class PaymentIntentRequest(BaseModel):
    appointment_id: UUID
    amount: float

class PaymentResponse(BaseModel):
    transaction_id: UUID
    status: str
    message: str

@router.post("/create-intent", response_model=PaymentResponse)
async def create_payment_intent(
    request: PaymentIntentRequest,
    db: AsyncSession = Depends(get_db),
    current_user: Any = Depends(deps.get_current_user)
) -> Any:
    """
    Initiate a secure payment session for an appointment.
    """
    transaction = await PaymentService.create_payment_intent(db, request.appointment_id, request.amount)
    return {
        "transaction_id": transaction.id,
        "status": transaction.status,
        "message": "Payment intent created successfully"
    }

@router.post("/confirm/{transaction_id}", response_model=PaymentResponse)
async def confirm_payment(
    transaction_id: UUID,
    db: AsyncSession = Depends(get_db),
    current_user: Any = Depends(deps.get_current_user)
) -> Any:
    """
    Simulate a successful payment confirmation from a provider.
    """
    transaction = await PaymentService.process_payment_success(db, transaction_id)
    if not transaction:
        raise HTTPException(status_code=404, detail="Transaction not found")

    return {
        "transaction_id": transaction.id,
        "status": transaction.status,
        "message": "Payment confirmed and appointment scheduled"
    }
