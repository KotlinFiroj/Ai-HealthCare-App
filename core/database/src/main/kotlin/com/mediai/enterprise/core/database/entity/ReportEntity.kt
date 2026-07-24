package com.mediai.enterprise.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val date: Long,
    val fileUrl: String,
    val summary: String? = null,
    val syncStatus: String
)
