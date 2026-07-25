package com.mediai.enterprise.feature.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mediai.enterprise.core.designsystem.icon.MediAIIcons
import com.mediai.enterprise.feature.home.domain.model.MetricType
import com.mediai.enterprise.feature.home.presentation.components.AiSuggestionCard
import com.mediai.enterprise.feature.home.presentation.components.HealthScoreCard
import com.mediai.enterprise.feature.home.presentation.components.MetricCard

fun HomeRoute(
    onNavigateToAppointments: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToReminders: () -> Unit,
    onNavigateToEmergency: () -> Unit,
    onNavigateToTimeline: () -> Unit,
    onNavigateToChat: () -> Unit,
    onNavigateToSymptomChecker: () -> Unit,
    onNavigateToRiskPrediction: () -> Unit,
    onNavigateToHealthCoach: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToConsultation: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(
        uiState = uiState,
        onNavigateToAppointments = onNavigateToAppointments,
        onNavigateToReports = onNavigateToReports,
        onNavigateToReminders = onNavigateToReminders,
        onNavigateToEmergency = onNavigateToEmergency,
        onNavigateToTimeline = onNavigateToTimeline,
        onNavigateToChat = onNavigateToChat,
        onNavigateToSymptomChecker = onNavigateToSymptomChecker,
        onNavigateToRiskPrediction = onNavigateToRiskPrediction,
        onNavigateToHealthCoach = onNavigateToHealthCoach,
        onNavigateToAnalytics = onNavigateToAnalytics,
        onNavigateToConsultation = onNavigateToConsultation
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    uiState: HomeUiState,
    onNavigateToAppointments: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToReminders: () -> Unit,
    onNavigateToEmergency: () -> Unit,
    onNavigateToTimeline: () -> Unit,
    onNavigateToChat: () -> Unit,
    onNavigateToSymptomChecker: () -> Unit,
    onNavigateToRiskPrediction: () -> Unit,
    onNavigateToHealthCoach: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToConsultation: () -> Unit
) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("MediAI Dashboard") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(MediAIIcons.Notifications, contentDescription = "Notifications")
                    }
                    IconButton(onClick = { }) {
                        Icon(MediAIIcons.Account, contentDescription = "Profile")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                uiState.data?.let { data ->
                    item {
                        HealthScoreCard(score = data.healthScore)
                    }

                    if (data.upcomingAppointment != null) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("Upcoming: ${data.upcomingAppointment}", style = MaterialTheme.typography.titleSmall)
                                        Text("Starts in 5 minutes", style = MaterialTheme.typography.bodySmall)
                                    }
                                    Button(onClick = onNavigateToConsultation) {
                                        Text("Join")
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            MediAIButton(
                                onClick = onNavigateToAppointments,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(MediAIIcons.Appointments, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Doctors")
                            }
                            MediAIButton(
                                onClick = onNavigateToReports,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(MediAIIcons.Check, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Reports")
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        MediAIButton(
                            onClick = onNavigateToReminders,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(MediAIIcons.Notifications, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Medicine Reminders")
                        }
                    }

                    item {
                        Button(
                            onClick = onNavigateToEmergency,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Icon(Icons.Default.MedicalInformation, contentDescription = null, tint = Color.White)
                            Spacer(Modifier.width(8.dp))
                            Text("Emergency Center (SOS)", color = Color.White)
                        }
                    }

                    item {
                        MediAIButton(
                            onClick = onNavigateToTimeline,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(MediAIIcons.Dashboard, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Health Timeline")
                        }
                    }

                    item {
                        OutlinedButton(
                            onClick = onNavigateToChat,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(MediAIIcons.Search, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Chat with MediAI")
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ElevatedButton(
                                onClick = onNavigateToSymptomChecker,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Symptom Checker")
                            }
                            ElevatedButton(
                                onClick = onNavigateToRiskPrediction,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Risk Prediction")
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = onNavigateToHealthCoach,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                            ) {
                                Text("AI Health Coach")
                            }
                            Button(
                                onClick = onNavigateToAnalytics,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                            ) {
                                Text("Health Analytics")
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Daily Metrics",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    item {
                        // Using a simple Column instead of a nested Grid for simplicity in LazyColumn
                        // In a real app, you'd probably use a custom layout or FlowRow
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            data.metrics.chunked(2).forEach { rowMetrics ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowMetrics.forEach { metric ->
                                        MetricCard(
                                            title = metric.type.name.replace("_", " "),
                                            value = metric.value,
                                            unit = metric.unit,
                                            icon = when (metric.type) {
                                                MetricType.STEPS -> MediAIIcons.Dashboard
                                                MetricType.WATER -> MediAIIcons.Add
                                                MetricType.SLEEP -> MediAIIcons.Home
                                                MetricType.HEART_RATE -> MediAIIcons.Appointments
                                                else -> MediAIIcons.Check
                                            },
                                            trend = metric.trend,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    if (rowMetrics.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            text = "AI Suggestions",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(data.suggestions) { suggestion ->
                        AiSuggestionCard(
                            title = suggestion.title,
                            description = suggestion.description,
                            priority = suggestion.priority
                        )
                    }
                }
            }
        }
    }
}
 Opt-in to Experimental Material 3 API for LargeTopAppBar
