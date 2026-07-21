package com.mediai.enterprise.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val AUTH_GRAPH_ROUTE = "auth_graph"
const val LOGIN_ROUTE = "login"
const val REGISTER_ROUTE = "register"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(LOGIN_ROUTE, navOptions)
}

fun NavController.navigateToRegister(navOptions: NavOptions? = null) {
    this.navigate(REGISTER_ROUTE, navOptions)
}

/**
 * [authGraph]
 * Defines the navigation graph for the authentication feature.
 */
fun NavGraphBuilder.authGraph(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToLogin: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit = {}
) {
    // We will use actual screen composables from :feature:auth
    // This requires interoperability between core:navigation and feature:auth
    // Usually, navigation is handled in the app module or a dedicated navigator.
}
