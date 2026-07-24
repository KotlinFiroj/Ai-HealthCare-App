package com.mediai.enterprise.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey val id: String,
    val doctorName: String,
    val specialization: String,
    val dateTime: Long,
    val status: String,
    val type: String
)
