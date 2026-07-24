package com.mediai.enterprise.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val EMERGENCY_ROUTE = "emergency"

fun NavController.navigateToEmergency(navOptions: NavOptions? = null) {
    this.navigate(EMERGENCY_ROUTE, navOptions)
}
