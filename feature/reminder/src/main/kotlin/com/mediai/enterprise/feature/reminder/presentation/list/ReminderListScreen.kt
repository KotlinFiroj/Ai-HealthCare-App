package com.mediai.enterprise.feature.reminder.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mediai.enterprise.core.database.entity.MedicineEntity
import com.mediai.enterprise.feature.reminder.presentation.ReminderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderTimelineRoute(
    onNavigateToAdd: () -> Unit,
    viewModel: ReminderViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    ReminderListScreen(
        uiState = uiState,
        onNavigateToAdd = onNavigateToAdd
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReminderListScreen(
    uiState: com.mediai.enterprise.feature.reminder.presentation.ReminderUiState,
    onNavigateToAdd: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Medicine Reminders") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(Icons.Default.Add, contentDescription = "Add Medicine")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.medicines) { medicine ->
                MedicineCard(medicine)
            }
        }
    }
}

@Composable
fun MedicineCard(medicine: MedicineEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = medicine.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Dosage: ${medicine.dosage}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Times: ${medicine.reminderTimes}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
