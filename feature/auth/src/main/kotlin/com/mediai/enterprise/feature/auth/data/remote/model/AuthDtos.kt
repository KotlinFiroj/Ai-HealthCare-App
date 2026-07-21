package com.mediai.enterprise.feature.auth.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val pass: String // Naming matched to a mock backend expectation
)

@Serializable
data class AuthResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDto
)

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val fullName: String,
    val phoneNumber: String? = null
)
