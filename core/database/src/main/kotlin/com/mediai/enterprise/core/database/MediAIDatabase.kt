package com.mediai.enterprise.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mediai.enterprise.core.database.dao.ChatDao
import com.mediai.enterprise.core.database.dao.EmergencyDao
import com.mediai.enterprise.core.database.dao.HealthDao
import com.mediai.enterprise.core.database.dao.MedicineDao
import com.mediai.enterprise.core.database.entity.*

/**
 * [MediAIDatabase]
 * The main Room database for the MediAI platform.
 */
@Database(
    entities = [
        MedicineEntity::class,
        EmergencyContactEntity::class,
        MedicalProfileEntity::class,
        ReportEntity::class,
        AppointmentEntity::class,
        ChatMessageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MediAIDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
    abstract fun emergencyDao(): EmergencyDao
    abstract fun healthDao(): HealthDao
    abstract fun chatDao(): ChatDao
}
