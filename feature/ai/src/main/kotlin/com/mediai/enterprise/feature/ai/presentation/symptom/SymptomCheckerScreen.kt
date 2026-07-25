package com.mediai.enterprise.feature.ai.presentation.symptom

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mediai.enterprise.feature.ai.presentation.AiViewModel
import com.mediai.enterprise.feature.ai.presentation.components.UrgencyBanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymptomCheckerRoute(
    onBack: () -> Unit,
    onEmergencyClick: () -> Unit,
    viewModel: AiViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    SymptomCheckerScreen(
        uiState = uiState,
        onCheckClick = viewModel::checkSymptoms,
        onBack = onBack,
        onEmergencyClick = onEmergencyClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SymptomCheckerScreen(
    uiState: com.mediai.enterprise.feature.ai.presentation.AiUiState,
    onCheckClick: (String) -> Unit,
    onBack: () -> Unit,
    onEmergencyClick: () -> Unit
) {
    var symptomsText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Symptom Checker") },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Describe how you're feeling",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = symptomsText,
                onValueChange = { symptomsText = it },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                placeholder = { Text("e.g., I have a persistent headache and feel dizzy since morning.") },
                maxLines = 10
            )

            Button(
                onClick = { onCheckClick(symptomsText) },
                modifier = Modifier.fillMaxWidth(),
                enabled = symptomsText.isNotBlank() && !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Analyze Symptoms")
                }
            }

            uiState.symptomAssessment?.let { assessment ->
                UrgencyBanner(level = assessment.urgency, onEmergencyClick = onEmergencyClick)

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Potential Conditions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        assessment.possibleConditions.forEach { condition ->
                            Text(text = "• $condition", style = MaterialTheme.typography.bodyMedium)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Recommended Specialist", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text(text = assessment.recommendedSpecialist, style = MaterialTheme.typography.bodyMedium)

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Advice", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text(text = assessment.lifestyleAdvice, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Text(
                    text = assessment.disclaimer,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            uiState.error?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
