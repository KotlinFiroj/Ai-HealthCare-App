package com.mediai.enterprise.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val SYMPTOM_CHECKER_ROUTE = "symptom_checker"
const val RISK_PREDICTION_ROUTE = "risk_prediction"

fun NavController.navigateToSymptomChecker(navOptions: NavOptions? = null) {
    this.navigate(SYMPTOM_CHECKER_ROUTE, navOptions)
}

fun NavController.navigateToRiskPrediction(navOptions: NavOptions? = null) {
    this.navigate(RISK_PREDICTION_ROUTE, navOptions)
}
