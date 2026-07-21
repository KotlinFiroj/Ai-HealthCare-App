package com.mediai.enterprise.feature.appointment.domain.repository

import com.mediai.enterprise.feature.appointment.domain.model.Appointment
import com.mediai.enterprise.feature.appointment.domain.model.Doctor
import com.mediai.enterprise.feature.appointment.domain.model.TimeSlot
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AppointmentRepository {
    suspend fun searchDoctors(query: String, specialization: String?): Result<List<Doctor>>
    suspend fun getDoctorDetails(doctorId: String): Result<Doctor>
    suspend fun getTimeSlots(doctorId: String, date: LocalDate): Result<List<TimeSlot>>
    suspend fun bookAppointment(doctorId: String, slot: TimeSlot): Result<Appointment>
    fun getAppointments(): Flow<List<Appointment>>
}
