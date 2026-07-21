package com.mediai.enterprise.feature.appointment.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mediai.enterprise.core.navigation.MediAINavDestinations
import com.mediai.enterprise.feature.appointment.presentation.AppointmentViewModel
import com.mediai.enterprise.feature.appointment.presentation.booking.BookingScreen
import com.mediai.enterprise.feature.appointment.presentation.details.DoctorDetailsScreen
import com.mediai.enterprise.feature.appointment.presentation.list.DoctorListScreen

fun NavGraphBuilder.appointmentGraph(
    navController: NavHostController
) {
    composable(route = MediAINavDestinations.APPOINTMENTS_ROUTE) {
        val viewModel: AppointmentViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        DoctorListScreen(
            uiState = uiState,
            onDoctorClick = { doctorId ->
                viewModel.selectDoctor(doctorId)
                navController.navigate("doctor_details")
            },
            onSearch = viewModel::searchDoctors,
            onBack = { navController.popBackStack() }
        )
    }

    composable(route = "doctor_details") {
        val viewModel: AppointmentViewModel = hiltViewModel(navController.getBackStackEntry(MediAINavDestinations.APPOINTMENTS_ROUTE))
        val uiState by viewModel.uiState.collectAsState()

        DoctorDetailsScreen(
            doctor = uiState.selectedDoctor,
            onBookClick = { navController.navigate("booking") },
            onBack = { navController.popBackStack() }
        )
    }

    composable(route = "booking") {
        val viewModel: AppointmentViewModel = hiltViewModel(navController.getBackStackEntry(MediAINavDestinations.APPOINTMENTS_ROUTE))
        val uiState by viewModel.uiState.collectAsState()

        if (uiState.bookingSuccess) {
            // Navigate to success or back to home
            navController.popBackStack(MediAINavDestinations.HOME_ROUTE, inclusive = false)
        }

        BookingScreen(
            uiState = uiState,
            onSlotSelected = viewModel::selectSlot,
            onConfirmClick = viewModel::bookAppointment,
            onBack = { navController.popBackStack() }
        )
    }
}
