package com.mediai.enterprise.feature.auth.domain.usecase

import com.mediai.enterprise.feature.auth.domain.model.User
import com.mediai.enterprise.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * [LoginUseCase]
 * Encapsulates the business logic for user login.
 */
class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password cannot be empty"))
        }
        return repository.login(email, password)
    }
}
