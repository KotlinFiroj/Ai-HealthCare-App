package com.mediai.enterprise.feature.reports.presentation.detail

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
import com.mediai.enterprise.feature.reports.presentation.components.QuestionList
import com.mediai.enterprise.feature.reports.presentation.components.RiskIndicatorCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportDetailRoute(
    reportId: String,
    onBack: () -> Unit,
    viewModel: ReportDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(reportId) {
        viewModel.loadReport(reportId)
    }

    ReportDetailScreen(
        uiState = uiState,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReportDetailScreen(
    uiState: ReportDetailUiState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.report?.title ?: "Report Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoading && !uiState.isAnalyzing) {
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
                // Report Info
                uiState.report?.let { report ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Category: ${report.category.displayName}", style = MaterialTheme.typography.labelLarge)
                            Text(text = "Date: ${report.date}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                if (uiState.isAnalyzing) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "AI is analyzing your report...", style = MaterialTheme.typography.labelMedium)
                    }
                }

                // AI Analysis
                uiState.analysis?.let { analysis ->
                    Text(
                        text = "AI Insights",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = analysis.summary,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    RiskIndicatorCard(risks = analysis.riskFactors)

                    QuestionList(questions = analysis.suggestedQuestions)

                    Text(
                        text = "AI Confidence Score: ${(analysis.confidenceScore * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.End)
                    )
                }

                uiState.error?.let { error ->
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
