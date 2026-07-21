package com.mediai.enterprise.feature.appointment.domain.model

data class Doctor(
    val id: String,
    val name: String,
    val specialization: String,
    val rating: Double,
    val reviewsCount: Int,
    val experienceYears: Int,
    val hospitalName: String,
    val about: String,
    val imageUrl: String? = null
)

enum class Specialization(val displayName: String) {
    CARDIOLOGY("Cardiology"),
    NEUROLOGY("Neurology"),
    PEDIATRICS("Pediatrics"),
    DERMATOLOGY("Dermatology"),
    ORTHOPEDICS("Orthopedics"),
    GENERAL("General")
}
