package com.mediai.enterprise.feature.healthtimeline.presentation.timeline

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mediai.enterprise.feature.healthtimeline.domain.model.TimelineItem
import com.mediai.enterprise.feature.healthtimeline.presentation.components.SummaryCard
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthTimelineRoute(
    onBack: () -> Unit,
    viewModel: TimelineViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    HealthTimelineScreen(
        uiState = uiState,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HealthTimelineScreen(
    uiState: TimelineUiState,
    onBack: () -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Health Timeline") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    SummaryCard(
                        summary = uiState.summary,
                        isGenerating = uiState.isGeneratingSummary
                    )
                }

                items(uiState.items) { item ->
                    TimelineCard(
                        item = item,
                        date = item.dateTime.format(dateFormatter)
                    )
                }
            }
        }
    }
}

@Composable
fun TimelineCard(
    item: TimelineItem,
    date: String
) {
    val (icon, tint) = when (item) {
        is TimelineItem.Report -> Icons.Default.Description to MaterialTheme.colorScheme.primary
        is TimelineItem.Appointment -> Icons.Default.Event to MaterialTheme.colorScheme.secondary
        is TimelineItem.Medication -> Icons.Default.MedicalServices to MaterialTheme.colorScheme.tertiary
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(32.dp)
            )

            Column {
                Text(
                    text = when (item) {
                        is TimelineItem.Report -> item.title
                        is TimelineItem.Appointment -> "Appointment: ${item.doctorName}"
                        is TimelineItem.Medication -> "Medication: ${item.name}"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (item is TimelineItem.Report && !item.summary.isNullOrBlank()) {
                    Text(
                        text = item.summary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}
