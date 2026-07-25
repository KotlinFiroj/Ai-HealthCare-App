package com.mediai.enterprise.feature.reports.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediai.enterprise.feature.reports.domain.model.MedicalReport
import com.mediai.enterprise.feature.reports.domain.model.ReportAnalysis
import com.mediai.enterprise.feature.reports.domain.repository.ReportRepository
import com.mediai.enterprise.feature.reports.domain.usecase.SummarizeReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportDetailViewModel @Inject constructor(
    private val repository: ReportRepository,
    private val summarizeReportUseCase: SummarizeReportUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun loadReport(reportId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val report = repository.getReportById(reportId)
            if (report != null) {
                _uiState.update { it.copy(report = report) }
                analyzeReport(reportId)
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Report not found") }
            }
        }
    }

    private fun analyzeReport(reportId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isAnalyzing = true) }
            val result = summarizeReportUseCase(reportId)
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, isAnalyzing = false, analysis = result.getOrNull()) }
            } else {
                _uiState.update { it.copy(isLoading = false, isAnalyzing = false, error = result.exceptionOrNull()?.message) }
            }
        }
    }
}

data class ReportDetailUiState(
    val report: MedicalReport? = null,
    val analysis: ReportAnalysis? = null,
    val isLoading: Boolean = false,
    val isAnalyzing: Boolean = false,
    val error: String? = null
)
