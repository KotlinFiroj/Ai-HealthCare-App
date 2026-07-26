package com.mediai.enterprise.feature.auth.presentation.biometric

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mediai.enterprise.core.designsystem.component.MediAIButton
import com.mediai.enterprise.core.security.BiometricType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiometricEnrollmentScreen(
    biometricType: BiometricType,
    onEnrollClick: () -> Unit,
    onSkipClick: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Identity Security") },
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
            Icon(
                imageVector = if (biometricType == BiometricType.FACE) Icons.Default.Face else Icons.Default.Fingerprint,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = if (biometricType == BiometricType.FACE) "Enable Face Unlock" else "Enable Fingerprint Login",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Secure your medical records with advanced biometrics. This adds an extra layer of clinical protection to your sensitive health data.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.weight(1f))

            MediAIButton(
                onClick = onEnrollClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Secure My Account")
            }

            TextButton(
                onClick = onSkipClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Not Now, Use Password Only")
            }
        }
    }
}
