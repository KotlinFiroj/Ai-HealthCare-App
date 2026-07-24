package com.mediai.enterprise.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val HEALTH_TIMELINE_ROUTE = "health_timeline"

fun NavController.navigateToHealthTimeline(navOptions: NavOptions? = null) {
    this.navigate(HEALTH_TIMELINE_ROUTE, navOptions)
}
