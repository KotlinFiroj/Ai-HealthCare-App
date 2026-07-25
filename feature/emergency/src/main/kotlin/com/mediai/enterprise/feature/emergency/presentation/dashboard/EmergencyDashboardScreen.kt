package com.mediai.enterprise.feature.emergency.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mediai.enterprise.feature.emergency.presentation.EmergencyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyDashboardRoute(
    onNavigateToMedicalId: () -> Unit,
    onNavigateToContacts: () -> Unit,
    onNavigateToNearbyHospitals: () -> Unit,
    viewModel: EmergencyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    EmergencyDashboardScreen(
        uiState = uiState,
        onSosClick = viewModel::triggerSos,
        onMedicalIdClick = onNavigateToMedicalId,
        onContactsClick = onNavigateToContacts,
        onNearbyHospitalsClick = onNavigateToNearbyHospitals
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EmergencyDashboardScreen(
    uiState: com.mediai.enterprise.feature.emergency.presentation.EmergencyUiState,
    onSosClick: () -> Unit,
    onMedicalIdClick: () -> Unit,
    onContactsClick: () -> Unit,
    onNearbyHospitalsClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Emergency Center") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = "Emergency Help Needed?",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            // SOS Button
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .clip(CircleShape)
                    .background(Color.Red.copy(alpha = 0.1f))
                    .padding(20.dp)
                    .clip(CircleShape)
                    .background(Color.Red.copy(alpha = 0.2f))
                    .padding(20.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
                    .clickable { onSosClick() },
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isSosTriggering) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(
                        text = "SOS",
                        color = Color.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Text(
                text = "Pressing SOS will send your location to emergency contacts",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EmergencyActionCard(
                    title = "Medical ID",
                    icon = Icons.Default.MedicalInformation,
                    onClick = onMedicalIdClick,
                    modifier = Modifier.weight(1f)
                )
                EmergencyActionCard(
                    title = "Contacts",
                    icon = Icons.Default.Contacts,
                    onClick = onContactsClick,
                    modifier = Modifier.weight(1f)
                )
            }

            MediAIButton(
                onClick = onNearbyHospitalsClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Find Nearby Hospitals")
            }
        }
    }
}

@Composable
fun EmergencyActionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier.height(120.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, style = MaterialTheme.typography.labelLarge)
        }
    }
}
