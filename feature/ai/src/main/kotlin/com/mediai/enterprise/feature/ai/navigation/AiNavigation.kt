package com.mediai.enterprise.feature.ai.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mediai.enterprise.core.navigation.EMERGENCY_ROUTE
import com.mediai.enterprise.core.navigation.RISK_PREDICTION_ROUTE
import com.mediai.enterprise.core.navigation.SYMPTOM_CHECKER_ROUTE
import com.mediai.enterprise.feature.ai.presentation.risk.RiskDashboardRoute
import com.mediai.enterprise.feature.ai.presentation.symptom.SymptomCheckerRoute

fun NavGraphBuilder.aiGraph(
    navController: NavController
) {
    composable(route = SYMPTOM_CHECKER_ROUTE) {
        SymptomCheckerRoute(
            onBack = { navController.popBackStack() },
            onEmergencyClick = {
                navController.navigate(EMERGENCY_ROUTE)
            }
        )
    }

    composable(route = RISK_PREDICTION_ROUTE) {
        RiskDashboardRoute(
            onBack = { navController.popBackStack() }
        )
    }
}
