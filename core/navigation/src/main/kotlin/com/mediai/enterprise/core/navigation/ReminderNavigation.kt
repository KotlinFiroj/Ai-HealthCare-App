package com.mediai.enterprise.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val REMINDERS_ROUTE = "reminders"

fun NavController.navigateToReminders(navOptions: NavOptions? = null) {
    this.navigate(REMINDERS_ROUTE, navOptions)
}
