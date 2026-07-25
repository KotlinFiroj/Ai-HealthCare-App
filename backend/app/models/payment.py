from sqlalchemy import Column, String, Float, ForeignKey, DateTime
from sqlalchemy.orm import relationship
from app.models.base import Base

class Transaction(Base):
    """
    [Transaction]
    Represents a financial transaction for a medical appointment.
    Follows basic PCI-DSS inspired data patterns.
    """
    appointment_id = Column(ForeignKey("appointment.id"), nullable=False)
    amount = Column(Float, nullable=False)
    currency = Column(String, default="USD")
    status = Column(String, default="PENDING") # PENDING, SUCCESS, FAILED
    provider_transaction_id = Column(String, nullable=True) # e.g. Stripe/PayPal ID

    appointment = relationship("Appointment", back_populates="transaction")
