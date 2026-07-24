package com.mediai.enterprise.core.database.dao

import androidx.room.*
import com.mediai.enterprise.core.database.entity.AppointmentEntity
import com.mediai.enterprise.core.database.entity.ReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDao {
    @Query("SELECT * FROM reports ORDER BY date DESC")
    fun getAllReports(): Flow<List<ReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: ReportEntity)

    @Query("SELECT * FROM appointments ORDER BY dateTime DESC")
    fun getAllAppointments(): Flow<List<AppointmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: AppointmentEntity)
}
