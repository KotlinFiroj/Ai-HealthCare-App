package com.mediai.enterprise.feature.analytics.presentation.coach

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mediai.enterprise.feature.analytics.presentation.AnalyticsViewModel
import com.mediai.enterprise.feature.analytics.presentation.components.WellnessGoalCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthCoachRoute(
    onBack: () -> Unit,
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadWellnessPlan()
    }

    HealthCoachScreen(
        uiState = uiState,
        onGoalToggle = viewModel::toggleGoal,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HealthCoachScreen(
    uiState: com.mediai.enterprise.feature.analytics.presentation.AnalyticsUiState,
    onGoalToggle: (com.mediai.enterprise.feature.analytics.domain.model.DailyGoal, Boolean) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Health Coach") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoadingPlan && uiState.wellnessPlan == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                uiState.wellnessPlan?.let { plan ->
                    Text(
                        text = "Your Wellness Blueprint",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Nutritional Focus", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(text = plan.nutritionalFocus, style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    Text(text = "Daily Goals", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    plan.dailyGoals.forEach { goal ->
                        WellnessGoalCard(
                            goal = goal,
                            onToggle = { isCompleted -> onGoalToggle(goal, isCompleted) }
                        )
                    }

                    Text(text = "Exercise Routine", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Card {
                        Text(
                            text = plan.exerciseRoutine,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Text(text = "Mental Wellbeing", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Card {
                        Text(
                            text = plan.mentalWellbeingTip,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Text(
                        text = plan.disclaimer,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}
