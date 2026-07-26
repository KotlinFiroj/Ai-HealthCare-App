package com.mediai.enterprise.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mediai.enterprise.core.navigation.MediAINavDestinations
import com.mediai.enterprise.feature.settings.presentation.language.LanguageSelectionScreen

const val SETTINGS_GRAPH_ROUTE = "settings_graph"
const val LANGUAGE_SELECTION_ROUTE = "language_selection"

fun NavController.navigateToLanguageSelection() {
    this.navigate(LANGUAGE_SELECTION_ROUTE)
}

fun NavGraphBuilder.settingsGraph(
    navController: NavController
) {
    composable(route = LANGUAGE_SELECTION_ROUTE) {
        LanguageSelectionScreen(
            onBack = { navController.popBackStack() }
        )
    }
}
