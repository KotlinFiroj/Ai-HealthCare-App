package com.mediai.enterprise.feature.emergency.presentation.medicalid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mediai.enterprise.core.database.entity.MedicalProfileEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalIdScreen(
    profile: MedicalProfileEntity?,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medical ID") },
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
                .background(Color.Red.copy(alpha = 0.05f))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MedicalIdItem(
                label = "BLOOD GROUP",
                value = profile?.bloodGroup ?: "Not set",
                isCritical = true
            )
            MedicalIdItem(
                label = "ALLERGIES",
                value = profile?.allergies ?: "None",
                isCritical = true
            )
            MedicalIdItem(
                label = "CHRONIC CONDITIONS",
                value = profile?.chronicConditions ?: "None"
            )
            MedicalIdItem(
                label = "CURRENT MEDICATIONS",
                value = profile?.currentMedications ?: "None"
            )
            MedicalIdItem(
                label = "EMERGENCY INSTRUCTIONS",
                value = profile?.emergencyInstructions ?: "No specific instructions"
            )
        }
    }
}

@Composable
fun MedicalIdItem(
    label: String,
    value: String,
    isCritical: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCritical) Color(0xFFFFEBEE) else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = if (isCritical) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (isCritical) Color.Red else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
