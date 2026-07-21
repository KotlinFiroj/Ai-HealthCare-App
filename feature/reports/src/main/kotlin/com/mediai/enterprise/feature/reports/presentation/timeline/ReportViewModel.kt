package com.mediai.enterprise.feature.reports.presentation.timeline

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediai.enterprise.feature.reports.domain.model.MedicalReport
import com.mediai.enterprise.feature.reports.domain.model.PrescriptionData
import com.mediai.enterprise.feature.reports.domain.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: ReportRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadReports()
    }

    fun loadReports() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getReports().collect { reports ->
                _uiState.update { it.copy(isLoading = false, reports = reports) }
            }
        }
    }

    fun scanPrescription(bitmap: Bitmap) {
        viewModelScope.launch {
            _uiState.update { it.copy(isScanning = true) }
            val result = repository.scanAndParsePrescription(bitmap)
            if (result.isSuccess) {
                _uiState.update { it.copy(isScanning = false, scannedPrescription = result.getOrNull()) }
            } else {
                _uiState.update { it.copy(isScanning = false, error = result.exceptionOrNull()?.message) }
            }
        }
    }
}

data class ReportUiState(
    val isLoading: Boolean = false,
    val reports: List<MedicalReport> = emptyList(),
    val isScanning: Boolean = false,
    val scannedPrescription: PrescriptionData? = null,
    val error: String? = null
)
