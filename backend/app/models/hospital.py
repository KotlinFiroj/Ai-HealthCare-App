from sqlalchemy import Column, String, Float
from app.models.base import Base

class Hospital(Base):
    name = Column(String, nullable=False)
    address = Column(String)
    latitude = Column(Float, nullable=False)
    longitude = Column(Float, nullable=False)
    contact_number = Column(String)
