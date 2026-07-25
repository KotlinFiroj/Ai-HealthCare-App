package com.mediai.enterprise.feature.appointment.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class DoctorDto(
    val id: String,
    val name: String,
    val specialization: String,
    val rating: Double,
    val reviews_count: Int,
    val experience_years: Int? = null,
    val hospital_name: String? = null,
    val about: String? = null
)

@Serializable
data class AppointmentRequestDto(
    val doctor_id: String,
    val date_time: String,
    val type: String = "VIDEO"
)

@Serializable
data class AppointmentResponseDto(
    val id: String,
    val doctor_id: String,
    val user_id: String,
    val date_time: String,
    val status: String,
    val type: String
)
