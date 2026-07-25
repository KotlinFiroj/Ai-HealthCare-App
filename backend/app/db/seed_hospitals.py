import asyncio
from sqlalchemy.ext.asyncio import AsyncSession
from app.core.database import AsyncSessionLocal
from app.models.hospital import Hospital

async def seed_hospitals():
    async with AsyncSessionLocal() as db:
        hospitals = [
            Hospital(name="City General Hospital", address="123 Main St", latitude=37.7749, longitude=-122.4194, contact_number="555-0101"),
            Hospital(name="St. Mary's Medical Center", address="456 Oak Ave", latitude=37.7849, longitude=-122.4294, contact_number="555-0102"),
            Hospital(name="Sunrise Wellness Clinic", address="789 Pine Rd", latitude=37.7649, longitude=-122.4094, contact_number="555-0103"),
        ]
        db.add_all(hospitals)
        await db.commit()
        print("Hospitals seeded successfully!")

if __name__ == "__main__":
    asyncio.run(seed_hospitals())
