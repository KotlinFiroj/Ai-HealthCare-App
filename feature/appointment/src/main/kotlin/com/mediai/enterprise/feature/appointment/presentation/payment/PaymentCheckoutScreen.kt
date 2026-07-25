package com.mediai.enterprise.feature.appointment.presentation.payment

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mediai.enterprise.core.designsystem.component.MediAIButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentCheckoutScreen(
    doctorName: String,
    amount: Double,
    onPaymentSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Secure Checkout") },
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Appointment with $doctorName",
                style = MaterialTheme.typography.titleLarge
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Total Amount", style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = "$${String.format("%.2f", amount)}",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Simulated Payment Form
            OutlinedTextField(
                value = "**** **** **** 4242",
                onValueChange = {},
                label = { Text("Card Number") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = "12/28",
                    onValueChange = {},
                    label = { Text("Expiry") },
                    modifier = Modifier.weight(1f),
                    enabled = false
                )
                OutlinedTextField(
                    value = "***",
                    onValueChange = {},
                    label = { Text("CVC") },
                    modifier = Modifier.weight(1f),
                    enabled = false
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                MediAIButton(
                    onClick = {
                        isLoading = true
                        // Simulate delay
                        onPaymentSuccess()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pay Now")
                }
            }

            Text(
                text = "Secured by MediAI Financial Services",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
