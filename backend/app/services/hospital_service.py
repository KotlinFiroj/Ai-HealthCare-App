from typing import List
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select
from app.models.hospital import Hospital
import math

class HospitalService:
    @staticmethod
    async def get_nearby_hospitals(
        db: AsyncSession,
        lat: float,
        lng: float,
        radius_km: float = 10.0
    ) -> List[Hospital]:
        """
        Retrieves hospitals within a given radius using the Haversine formula.
        In production, use PostGIS for high-performance spatial queries.
        """
        result = await db.execute(select(Hospital))
        hospitals = result.scalars().all()

        nearby = []
        for h in hospitals:
            dist = HospitalService.haversine(lat, lng, h.latitude, h.longitude)
            if dist <= radius_km:
                nearby.append(h)

        return nearby

    @staticmethod
    def haversine(lat1, lon1, lat2, lon2):
        R = 6371  # Earth radius in km
        dlat = math.radians(lat2 - lat1)
        dlon = math.radians(lon2 - lon1)
        a = math.sin(dlat / 2) ** 2 + math.cos(math.radians(lat1)) * \
            math.cos(math.radians(lat2)) * math.sin(dlon / 2) ** 2
        c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
        return R * c
