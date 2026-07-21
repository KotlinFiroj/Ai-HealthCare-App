package com.mediai.enterprise.feature.reports.presentation.timeline

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mediai.enterprise.feature.reports.presentation.components.ReportCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportTimelineRoute(
    onNavigateToScan: () -> Unit,
    onReportClick: (String) -> Unit,
    viewModel: ReportViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    ReportTimelineScreen(
        uiState = uiState,
        onNavigateToScan = onNavigateToScan,
        onReportClick = onReportClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReportTimelineScreen(
    uiState: ReportUiState,
    onNavigateToScan: () -> Unit,
    onReportClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medical Reports") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToScan) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Scan Report")
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.reports) { report ->
                    ReportCard(
                        report = report,
                        onClick = { onReportClick(report.id) }
                    )
                }
            }
        }
    }
}
