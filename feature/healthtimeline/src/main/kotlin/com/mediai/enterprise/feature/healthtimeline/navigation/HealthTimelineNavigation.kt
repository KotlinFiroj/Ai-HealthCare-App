package com.mediai.enterprise.feature.healthtimeline.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mediai.enterprise.core.navigation.HEALTH_TIMELINE_ROUTE
import com.mediai.enterprise.feature.healthtimeline.presentation.timeline.HealthTimelineRoute

fun NavGraphBuilder.healthTimelineGraph(
    navController: NavController
) {
    composable(route = HEALTH_TIMELINE_ROUTE) {
        HealthTimelineRoute(
            onBack = { navController.popBackStack() }
        )
    }
}
