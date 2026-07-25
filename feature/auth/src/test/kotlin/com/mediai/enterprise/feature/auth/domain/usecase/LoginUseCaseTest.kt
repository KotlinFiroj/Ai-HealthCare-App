package com.mediai.enterprise.feature.auth.domain.usecase

import com.mediai.enterprise.feature.auth.domain.model.User
import com.mediai.enterprise.feature.auth.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginUseCaseTest {

    private val repository = mockk<AuthRepository>()
    private val loginUseCase = LoginUseCase(repository)

    @Test
    fun `login with empty email returns failure`() = runBlocking {
        val result = loginUseCase("", "password")
        assertTrue(result.isFailure)
        assertEquals("Email and password cannot be empty", result.exceptionOrNull()?.message)
    }

    @Test
    fun `login with empty password returns failure`() = runBlocking {
        val result = loginUseCase("test@example.com", "")
        assertTrue(result.isFailure)
        assertEquals("Email and password cannot be empty", result.exceptionOrNull()?.message)
    }

    @Test
    fun `login with valid credentials returns success`() = runBlocking {
        val user = User("1", "test@example.com", "Test User")
        coEvery { repository.login(any(), any()) } returns Result.success(user)

        val result = loginUseCase("test@example.com", "password")
        assertTrue(result.isSuccess)
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `login with invalid credentials returns failure`() = runBlocking {
        coEvery { repository.login(any(), any()) } returns Result.failure(Exception("Invalid credentials"))

        val result = loginUseCase("test@example.com", "wrong")
        assertTrue(result.isFailure)
        assertEquals("Invalid credentials", result.exceptionOrNull()?.message)
    }
}
