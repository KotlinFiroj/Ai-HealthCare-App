package com.mediai.enterprise.feature.appointment.domain.model

import java.time.LocalDateTime

data class Appointment(
    val id: String,
    val doctorId: String,
    val dateTime: LocalDateTime,
    val status: AppointmentStatus,
    val type: AppointmentType
)

enum class AppointmentStatus {
    UPCOMING, COMPLETED, CANCELLED
}

enum class AppointmentType {
    VIDEO, IN_PERSON
}

data class TimeSlot(
    val startTime: LocalDateTime,
    val isAvailable: Boolean
)
