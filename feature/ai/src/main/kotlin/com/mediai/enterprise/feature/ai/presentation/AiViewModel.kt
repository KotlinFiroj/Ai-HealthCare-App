package com.mediai.enterprise.feature.ai.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediai.enterprise.feature.ai.domain.model.*
import com.mediai.enterprise.feature.ai.domain.usecase.CheckSymptomsUseCase
import com.mediai.enterprise.feature.ai.domain.usecase.GetRiskPredictionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AiViewModel @Inject constructor(
    private val checkSymptomsUseCase: CheckSymptomsUseCase,
    private val getRiskPredictionsUseCase: GetRiskPredictionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiUiState())
    val uiState = _uiState.asStateFlow()

    fun checkSymptoms(symptoms: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = checkSymptomsUseCase(symptoms)
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, symptomAssessment = result.getOrNull()) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message) }
            }
        }
    }

    fun loadRisks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = getRiskPredictionsUseCase("mock_user_data")
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, risks = result.getOrDefault(emptyList())) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message) }
            }
        }
    }
}

data class AiUiState(
    val isLoading: Boolean = false,
    val symptomAssessment: SymptomAssessment? = null,
    val risks: List<RiskPrediction> = emptyList(),
    val error: String? = null
)
