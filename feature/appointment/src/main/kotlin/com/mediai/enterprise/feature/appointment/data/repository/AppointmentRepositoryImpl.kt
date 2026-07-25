package com.mediai.enterprise.feature.appointment.data.repository

import com.mediai.enterprise.core.data.sync.SyncManager
import com.mediai.enterprise.feature.appointment.data.remote.AppointmentApiService
import com.mediai.enterprise.feature.appointment.data.remote.model.AppointmentRequestDto
import com.mediai.enterprise.feature.appointment.domain.model.*
import com.mediai.enterprise.feature.appointment.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(
    private val apiService: AppointmentApiService,
    private val syncManager: SyncManager
) : AppointmentRepository {

    override suspend fun searchDoctors(query: String, specialization: String?): Result<List<Doctor>> {
        return try {
            val response = apiService.searchDoctors(query, specialization)
            Result.success(response.map { dto ->
                Doctor(
                    id = dto.id,
                    name = dto.name,
                    specialization = dto.specialization,
                    rating = dto.rating,
                    reviewsCount = dto.reviews_count,
                    experienceYears = dto.experience_years ?: 0,
                    hospitalName = dto.hospital_name ?: "",
                    about = dto.about ?: ""
                )
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDoctorDetails(doctorId: String): Result<Doctor> {
        return try {
            val dto = apiService.getDoctor(doctorId)
            Result.success(
                Doctor(
                    id = dto.id,
                    name = dto.name,
                    specialization = dto.specialization,
                    rating = dto.rating,
                    reviewsCount = dto.reviews_count,
                    experienceYears = dto.experience_years ?: 0,
                    hospitalName = dto.hospital_name ?: "",
                    about = dto.about ?: ""
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTimeSlots(doctorId: String, date: LocalDate): Result<List<TimeSlot>> {
        // Simulated local logic for slots
        val slots = (9..17).map { hour ->
            TimeSlot(LocalDateTime.of(date, LocalTime.of(hour, 0)), true)
        }
        return Result.success(slots)
    }

    override suspend fun bookAppointment(doctorId: String, slot: TimeSlot): Result<Appointment> {
        return try {
            val request = AppointmentRequestDto(
                doctor_id = doctorId,
                date_time = slot.startTime.toString()
            )
            val response = apiService.bookAppointment(request)
            syncManager.triggerSync()
            Result.success(
                Appointment(
                    id = response.id,
                    doctorId = response.doctor_id,
                    dateTime = LocalDateTime.parse(response.date_time),
                    status = AppointmentStatus.valueOf(response.status),
                    type = AppointmentType.valueOf(response.type)
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getAppointments(): Flow<List<Appointment>> = flowOf(emptyList())
}
