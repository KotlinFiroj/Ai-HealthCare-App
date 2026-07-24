package com.mediai.enterprise.feature.emergency.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mediai.enterprise.core.navigation.EMERGENCY_ROUTE
import com.mediai.enterprise.feature.emergency.presentation.EmergencyViewModel
import com.mediai.enterprise.feature.emergency.presentation.dashboard.EmergencyDashboardRoute
import com.mediai.enterprise.feature.emergency.presentation.medicalid.MedicalIdScreen

fun NavGraphBuilder.emergencyGraph(
    navController: NavController
) {
    composable(route = EMERGENCY_ROUTE) {
        EmergencyDashboardRoute(
            onNavigateToMedicalId = { navController.navigate("medical_id") },
            onNavigateToContacts = { /* Navigate to contacts */ }
        )
    }

    composable(route = "medical_id") {
        val viewModel: EmergencyViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        MedicalIdScreen(
            profile = uiState.medicalProfile,
            onBack = { navController.popBackStack() }
        )
    }
}
