package com.mediai.enterprise.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [MedicineEntity]
 * Represents a medication schedule in the local database.
 */
@Entity(tableName = "medicines")
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val dosage: String,
    val frequency: String,
    val reminderTimes: String, // Comma-separated times, e.g., "08:00,14:00,20:00"
    val startDate: Long,
    val endDate: Long,
    val isCompleted: Boolean = false
)
