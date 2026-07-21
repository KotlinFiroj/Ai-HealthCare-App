package com.mediai.enterprise.feature.appointment.presentation.booking

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mediai.enterprise.feature.appointment.presentation.AppointmentUiState
import com.mediai.enterprise.feature.appointment.presentation.components.SlotSelectionGrid
import com.mediai.enterprise.core.designsystem.component.MediAIButton
import com.mediai.enterprise.feature.appointment.domain.model.TimeSlot

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    uiState: AppointmentUiState,
    onSlotSelected: (TimeSlot) -> Unit,
    onConfirmClick: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Time Slot") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Available Slots for Today",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            SlotSelectionGrid(
                slots = uiState.availableSlots,
                selectedSlot = uiState.selectedSlot,
                onSlotSelected = onSlotSelected,
                modifier = Modifier.weight(1f)
            )

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.CenterHorizontally))
            }

            MediAIButton(
                onClick = onConfirmClick,
                enabled = uiState.selectedSlot != null && !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm Booking")
            }
        }
    }
}
