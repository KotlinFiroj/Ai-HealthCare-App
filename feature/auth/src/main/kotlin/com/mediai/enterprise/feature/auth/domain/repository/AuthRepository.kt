package com.mediai.enterprise.feature.auth.domain.repository

import com.mediai.enterprise.feature.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * [AuthRepository]
 * Interface for authentication data operations.
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String, fullName: String): Result<User>
    suspend fun logout()
    fun isUserLoggedIn(): Flow<Boolean>
}
