import random
import string
import redis.asyncio as redis
from app.core.config import settings
from typing import Optional

class OtpService:
    """
    [OtpService]
    Generates and verifies 6-digit One-Time Passwords.
    Uses Redis for temporary storage and expiration.
    """
    def __init__(self):
        self.redis_client = redis.from_url(settings.REDIS_URL)

    async def generate_otp(self, user_id: str) -> str:
        """Generates a 6-digit OTP and stores it in Redis for 5 minutes."""
        otp = ''.join(random.choices(string.digits, k=6))
        await self.redis_client.setex(f"otp_{user_id}", 300, otp)
        return otp

    async def verify_otp(self, user_id: str, otp: str) -> bool:
        """Verifies the provided OTP against the one stored in Redis."""
        stored_otp = await self.redis_client.get(f"otp_{user_id}")
        if stored_otp and stored_otp.decode('utf-8') == otp:
            await self.redis_client.delete(f"otp_{user_id}")
            return True
        return False

otp_service = OtpService()
