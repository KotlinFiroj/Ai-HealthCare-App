package com.mediai.enterprise.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mediai.enterprise.core.navigation.MediAINavDestinations
import com.mediai.enterprise.feature.home.presentation.HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(MediAINavDestinations.HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeGraph(
    onNavigateToAppointments: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToReminders: () -> Unit,
    onNavigateToEmergency: () -> Unit
) {
    composable(route = MediAINavDestinations.HOME_ROUTE) {
        HomeRoute(
            onNavigateToAppointments = onNavigateToAppointments,
            onNavigateToReports = onNavigateToReports,
            onNavigateToReminders = onNavigateToReminders,
            onNavigateToEmergency = onNavigateToEmergency
        )
    }
}
