from pydantic_settings import BaseSettings
from typing import Optional

class Settings(BaseSettings):
    PROJECT_NAME: str = "MediAI Enterprise API"
    API_V1_STR: str = "/api/v1"

    # Security
    SECRET_KEY: str = "super_secret_enterprise_key_12345" # Change in production
    ALGORITHM: str = "HS256"
    ACCESS_TOKEN_EXPIRE_MINUTES: int = 60 * 24 * 7 # 1 week

    # Database
    DATABASE_URL: str = "postgresql+asyncpg://postgres:postgres@db:5432/mediai_db"

    # External APIs
    GEMINI_API_KEY: Optional[str] = None

    # Infrastructure
    REDIS_URL: str = "redis://redis:6379/0"
    CHROMA_URL: str = "http://chromadb:8000"

    class Config:
        env_file = ".env"

settings = Settings()
