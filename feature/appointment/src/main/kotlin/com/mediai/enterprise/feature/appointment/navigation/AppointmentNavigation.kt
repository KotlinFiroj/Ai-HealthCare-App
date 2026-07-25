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

import com.mediai.enterprise.core.navigation.QR_CHECKIN_ROUTE
import com.mediai.enterprise.core.navigation.PAYMENT_ROUTE
import com.mediai.enterprise.core.navigation.CONSULTATION_ROUTE
import com.mediai.enterprise.feature.appointment.presentation.checkin.QrCheckInScreen
import com.mediai.enterprise.feature.appointment.presentation.payment.PaymentCheckoutScreen
import com.mediai.enterprise.feature.appointment.presentation.telehealth.ConsultationRoomScreen

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
            onQrCheckInClick = { navController.navigate(QR_CHECKIN_ROUTE) },
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
            navController.navigate(PAYMENT_ROUTE)
        }

        BookingScreen(
            uiState = uiState,
            onSlotSelected = viewModel::selectSlot,
            onConfirmClick = viewModel::bookAppointment,
            onBack = { navController.popBackStack() }
        )
    }

    composable(route = PAYMENT_ROUTE) {
        val viewModel: AppointmentViewModel = hiltViewModel(navController.getBackStackEntry(MediAINavDestinations.APPOINTMENTS_ROUTE))
        val uiState by viewModel.uiState.collectAsState()

        PaymentCheckoutScreen(
            doctorName = uiState.selectedDoctor?.name ?: "Doctor",
            amount = 50.0, // Standard Consultation Fee
            onPaymentSuccess = {
                navController.navigate(MediAINavDestinations.HOME_ROUTE)
            },
            onBack = { navController.popBackStack() }
        )
    }

    composable(route = CONSULTATION_ROUTE) {
        ConsultationRoomScreen(
            doctorName = "Dr. Sarah Smith",
            onEndCall = { navController.popBackStack() }
        )
    }

    composable(route = QR_CHECKIN_ROUTE) {
        QrCheckInScreen(
            appointmentId = "APP-12345",
            onBack = { navController.popBackStack() }
        )
    }
}
