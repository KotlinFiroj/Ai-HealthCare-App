package com.mediai.enterprise.feature.appointment.presentation.checkin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mediai.enterprise.core.designsystem.component.MediAIButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrCheckInScreen(
    appointmentId: String,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hospital Check-in") },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Show this QR at the Reception",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // QR Code Placeholder
            Surface(
                modifier = Modifier.size(250.dp),
                color = Color.LightGray.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.medium
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("QR: $appointmentId")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Appointment ID: $appointmentId",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.weight(1f))

            MediAIButton(
                onClick = { /* Launch simulated scanner */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simulate Staff Scan")
            }
        }
    }
}
