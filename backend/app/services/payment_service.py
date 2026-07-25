from typing import Optional
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select
from app.models.payment import Transaction
from app.models.appointment import Appointment
from uuid import UUID
import logging

class PaymentService:
    """
    [PaymentService]
    Handles the orchestration of secure financial transactions.
    In production, this would integrate with Stripe or PayPal SDKs.
    """
    @staticmethod
    async def create_payment_intent(db: AsyncSession, appointment_id: UUID, amount: float) -> Transaction:
        db_obj = Transaction(
            appointment_id=appointment_id,
            amount=amount,
            status="PENDING"
        )
        db.add(db_obj)
        await db.commit()
        await db.refresh(db_obj)
        return db_obj

    @staticmethod
    async def process_payment_success(db: AsyncSession, transaction_id: UUID) -> Optional[Transaction]:
        result = await db.execute(select(Transaction).where(Transaction.id == transaction_id))
        transaction = result.scalars().first()
        if not transaction:
            return None

        transaction.status = "SUCCESS"

        # Automatically update the appointment status
        result = await db.execute(select(Appointment).where(Appointment.id == transaction.appointment_id))
        appointment = result.scalars().first()
        if appointment:
            appointment.status = "CONFIRMED"

        await db.commit()
        await db.refresh(transaction)
        return transaction
