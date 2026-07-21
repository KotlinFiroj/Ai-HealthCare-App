package com.mediai.enterprise.feature.auth.data.repository

import com.mediai.enterprise.core.security.TokenManager
import com.mediai.enterprise.feature.auth.data.remote.AuthApiService
import com.mediai.enterprise.feature.auth.data.remote.model.LoginRequestDto
import com.mediai.enterprise.feature.auth.domain.model.User
import com.mediai.enterprise.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * [AuthRepositoryImpl]
 * Implementation of [AuthRepository] using a remote API.
 */
class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = apiService.login(LoginRequestDto(email, password))
            tokenManager.saveAccessToken(response.accessToken)
            tokenManager.saveRefreshToken(response.refreshToken)
            Result.success(
                User(
                    id = response.user.id,
                    email = response.user.email,
                    fullName = response.user.fullName,
                    phoneNumber = response.user.phoneNumber
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String, fullName: String): Result<User> {
        // Implementation for registration
        return Result.failure(NotImplementedError("Registration not yet implemented"))
    }

    override suspend fun logout() {
        tokenManager.clearTokens()
    }

    override fun isUserLoggedIn(): Flow<Boolean> = flow {
        emit(tokenManager.getAccessToken() != null)
    }
}
