package com.mediai.enterprise.feature.appointment.data.repository

import com.mediai.enterprise.core.data.sync.SyncManager
import com.mediai.enterprise.feature.appointment.domain.model.*
import com.mediai.enterprise.feature.appointment.domain.repository.AppointmentRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(
    private val syncManager: SyncManager
) : AppointmentRepository {

    private val mockDoctors = listOf(
        Doctor("1", "Dr. Sarah Smith", "Cardiologist", 4.8, 120, 15, "City General Hospital", "Expert in cardiac surgery and heart health."),
        Doctor("2", "Dr. James Wilson", "Neurologist", 4.9, 85, 12, "Metro Health Center", "Specializes in neurodegenerative disorders."),
        Doctor("3", "Dr. Emily Brown", "Pediatrician", 4.7, 200, 10, "Children's Hospital", "Dedicated to child health and wellness."),
        Doctor("4", "Dr. Michael Chen", "Dermatologist", 4.6, 150, 8, "Skin & Laser Center", "Expert in medical and cosmetic dermatology."),
        Doctor("5", "Dr. Robert Lee", "Orthopedic", 4.8, 95, 20, "Sports Medicine Clinic", "Specializes in joint replacement and sports injuries.")
    )

    override suspend fun searchDoctors(query: String, specialization: String?): Result<List<Doctor>> {
        delay(1000)
        val filtered = mockDoctors.filter {
            (specialization == null || it.specialization == specialization) &&
            it.name.contains(query, ignoreCase = true)
        }
        return Result.success(filtered)
    }

    override suspend fun getDoctorDetails(doctorId: String): Result<Doctor> {
        delay(500)
        val doctor = mockDoctors.find { it.id == doctorId }
        return if (doctor != null) Result.success(doctor) else Result.failure(Exception("Doctor not found"))
    }

    override suspend fun getTimeSlots(doctorId: String, date: LocalDate): Result<List<TimeSlot>> {
        delay(500)
        val slots = (9..17).map { hour ->
            TimeSlot(LocalDateTime.of(date, LocalTime.of(hour, 0)), true)
        }
        return Result.success(slots)
    }

    override suspend fun bookAppointment(doctorId: String, slot: TimeSlot): Result<Appointment> {
        delay(1000)
        syncManager.triggerSync()
        return Result.success(
            Appointment(
                id = UUID.randomUUID().toString(),
                doctorId = doctorId,
                dateTime = slot.startTime,
                status = AppointmentStatus.UPCOMING,
                type = AppointmentType.VIDEO
            )
        )
    }

    override fun getAppointments(): Flow<List<Appointment>> = flowOf(emptyList())
}
