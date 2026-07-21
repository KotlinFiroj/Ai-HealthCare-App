package com.mediai.enterprise.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mediai.enterprise.core.navigation.MediAINavDestinations
import com.mediai.enterprise.feature.auth.presentation.login.LoginRoute
import com.mediai.enterprise.feature.auth.presentation.register.RegisterScreen

/**
 * [authGraph]
 * NavGraph implementation for the Auth feature.
 */
fun NavGraphBuilder.authGraph(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    composable(route = MediAINavDestinations.AUTH_ROUTE) {
        LoginRoute(
            onLoginSuccess = onLoginSuccess,
            onNavigateToRegister = onNavigateToRegister
        )
    }

    composable(route = "register") {
        RegisterScreen()
    }
}
