package com.mediai.enterprise.core.network

import com.mediai.enterprise.core.security.TokenManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

/**
 * [TokenAuthenticator]
 * Automatically handles 401 Unauthorized responses by attempting to refresh the token.
 */
class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // 1. Get the current refresh token
        val refreshToken = tokenManager.getRefreshToken() ?: return null

        // 2. Synchronously call the refresh token endpoint
        // val newToken = api.refreshToken(refreshToken).execute()

        // Mock implementation for now
        val newToken = "mock_new_access_token"

        // 3. Save new tokens
        tokenManager.saveAccessToken(newToken)

        // 4. Retry the failed request with the new token
        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }
}
