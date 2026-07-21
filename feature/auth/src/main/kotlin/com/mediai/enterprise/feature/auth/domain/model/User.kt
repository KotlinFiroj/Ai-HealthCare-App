package com.mediai.enterprise.feature.auth.domain.model

/**
 * [User]
 * Domain model for a User in the MediAI platform.
 */
data class User(
    val id: String,
    val email: String,
    val fullName: String,
    val phoneNumber: String? = null,
    val profileImageUrl: String? = null
)
