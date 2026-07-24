package com.mediai.enterprise.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medical_profile")
data class MedicalProfileEntity(
    @PrimaryKey val userId: String,
    val bloodGroup: String,
    val allergies: String,
    val chronicConditions: String,
    val currentMedications: String,
    val emergencyInstructions: String
)
