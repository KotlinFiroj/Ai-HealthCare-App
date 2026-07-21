package com.mediai.enterprise.feature.appointment.domain.usecase

import com.mediai.enterprise.feature.appointment.domain.model.Appointment
import com.mediai.enterprise.feature.appointment.domain.model.TimeSlot
import com.mediai.enterprise.feature.appointment.domain.repository.AppointmentRepository
import javax.inject.Inject

class BookAppointmentUseCase @Inject constructor(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(doctorId: String, slot: TimeSlot): Result<Appointment> {
        return repository.bookAppointment(doctorId, slot)
    }
}
