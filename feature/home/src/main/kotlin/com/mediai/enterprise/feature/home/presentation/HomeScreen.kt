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

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(uiState = uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    uiState: HomeUiState
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
