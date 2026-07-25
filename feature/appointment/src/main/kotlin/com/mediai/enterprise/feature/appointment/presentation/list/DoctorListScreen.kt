package com.mediai.enterprise.feature.appointment.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mediai.enterprise.feature.appointment.presentation.AppointmentUiState
import com.mediai.enterprise.feature.appointment.presentation.components.DoctorCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorListScreen(
    uiState: AppointmentUiState,
    onDoctorClick: (String) -> Unit,
    onSearch: (String) -> Unit,
    onQrCheckInClick: () -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Find a Doctor") },
                actions = {
                    IconButton(onClick = onQrCheckInClick) {
                        Icon(androidx.compose.material.icons.Icons.Default.QrCode, contentDescription = "Check-in")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    onSearch(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search by name or specialization") },
                singleLine = true
            )

            if (uiState.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.doctors) { doctor ->
                    DoctorCard(
                        doctor = doctor,
                        onClick = { onDoctorClick(doctor.id) }
                    )
                }
            }
        }
    }
}
