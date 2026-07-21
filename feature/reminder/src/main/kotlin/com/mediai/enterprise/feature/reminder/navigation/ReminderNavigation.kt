package com.mediai.enterprise.feature.reminder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mediai.enterprise.core.navigation.REMINDERS_ROUTE
import com.mediai.enterprise.feature.reminder.presentation.list.ReminderTimelineRoute

fun NavGraphBuilder.reminderGraph(
    navController: NavController
) {
    composable(route = REMINDERS_ROUTE) {
        ReminderTimelineRoute(
            onNavigateToAdd = {
                // Navigate to AddMedicineScreen
            }
        )
    }
}
