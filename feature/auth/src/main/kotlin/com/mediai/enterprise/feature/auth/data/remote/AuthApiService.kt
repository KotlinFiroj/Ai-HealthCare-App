package com.mediai.enterprise.feature.auth.data.remote

import com.mediai.enterprise.feature.auth.data.remote.model.AuthResponseDto
import com.mediai.enterprise.feature.auth.data.remote.model.LoginRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * [AuthApiService]
 * Retrofit interface for Auth endpoints.
 */
interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): AuthResponseDto

    @POST("auth/register")
    suspend fun register(@Body request: Map<String, String>): AuthResponseDto
}
