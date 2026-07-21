package com.mediai.enterprise.feature.reports.presentation.scan

import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mediai.enterprise.feature.reports.presentation.components.DocumentScanner
import com.mediai.enterprise.feature.reports.presentation.components.captureImage
import com.mediai.enterprise.feature.reports.presentation.timeline.ReportViewModel

@Composable
fun ScanScreen(
    onScanComplete: () -> Unit,
    onBack: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val imageCapture = remember { ImageCapture.Builder().build() }
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        DocumentScanner(
            onImageCaptured = { bitmap ->
                viewModel.scanPrescription(bitmap)
            },
            imageCapture = imageCapture,
            modifier = Modifier.fillMaxSize()
        )

        // Capture Button
        FloatingActionButton(
            onClick = {
                captureImage(imageCapture, context) { bitmap ->
                    viewModel.scanPrescription(bitmap)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Icon(Icons.Default.Camera, contentDescription = "Capture")
        }

        if (uiState.isScanning) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("AI is parsing your prescription...")
                    }
                }
            }
        }

        if (uiState.scannedPrescription != null) {
            // In a real app, navigate to a review screen
            LaunchedEffect(uiState.scannedPrescription) {
                onScanComplete()
            }
        }
    }
}
