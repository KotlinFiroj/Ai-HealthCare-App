package com.mediai.enterprise.feature.analytics.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mediai.enterprise.core.navigation.ANALYTICS_DASHBOARD_ROUTE
import com.mediai.enterprise.core.navigation.HEALTH_COACH_ROUTE
import com.mediai.enterprise.feature.analytics.presentation.coach.HealthCoachRoute
import com.mediai.enterprise.feature.analytics.presentation.dashboard.AnalyticsDashboardRoute

fun NavGraphBuilder.analyticsGraph(
    navController: NavController
) {
    composable(route = HEALTH_COACH_ROUTE) {
        HealthCoachRoute(
            onBack = { navController.popBackStack() }
        )
    }

    composable(route = ANALYTICS_DASHBOARD_ROUTE) {
        AnalyticsDashboardRoute(
            onBack = { navController.popBackStack() }
        )
    }
}
