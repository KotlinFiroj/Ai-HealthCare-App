package com.mediai.enterprise.feature.emergency.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mediai.enterprise.core.navigation.EMERGENCY_ROUTE
import com.mediai.enterprise.core.navigation.HOSPITAL_MAP_ROUTE
import com.mediai.enterprise.feature.emergency.presentation.EmergencyViewModel
import com.mediai.enterprise.feature.emergency.presentation.dashboard.EmergencyDashboardRoute
import com.mediai.enterprise.feature.emergency.presentation.medicalid.MedicalIdScreen
import com.mediai.enterprise.feature.emergency.presentation.map.HospitalMapScreen
import com.google.android.gms.maps.model.LatLng

fun NavGraphBuilder.emergencyGraph(
    navController: NavController
) {
    composable(route = EMERGENCY_ROUTE) {
        EmergencyDashboardRoute(
            onNavigateToMedicalId = { navController.navigate("medical_id") },
            onNavigateToContacts = { /* Navigate to contacts */ },
            onNavigateToNearbyHospitals = { navController.navigate(HOSPITAL_MAP_ROUTE) }
        )
    }

    composable(route = HOSPITAL_MAP_ROUTE) {
        HospitalMapScreen(
            userLocation = LatLng(37.7749, -122.4194), // Mock Location
            hospitals = listOf(LatLng(37.7849, -122.4294)),
            onBack = { navController.popBackStack() }
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
