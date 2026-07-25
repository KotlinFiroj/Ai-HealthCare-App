package com.mediai.enterprise.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val HEALTH_COACH_ROUTE = "health_coach"
const val ANALYTICS_DASHBOARD_ROUTE = "analytics_dashboard"

fun NavController.navigateToHealthCoach(navOptions: NavOptions? = null) {
    this.navigate(HEALTH_COACH_ROUTE, navOptions)
}

fun NavController.navigateToAnalyticsDashboard(navOptions: NavOptions? = null) {
    this.navigate(ANALYTICS_DASHBOARD_ROUTE, navOptions)
}
