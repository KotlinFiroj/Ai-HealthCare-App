from typing import List, Dict
from pydantic import BaseModel

class HealthSummary(BaseModel):
    health_score: int
    summary_text: str
    risk_level: str

class TrendPoint(BaseModel):
    label: str
    value: float

class TrendData(BaseModel):
    metric_name: str
    points: List[TrendPoint]

class AdherenceScore(BaseModel):
    score: float # 0 to 1
    missed_count: int
    taken_count: int
