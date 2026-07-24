package com.mediai.enterprise.feature.healthtimeline.presentation.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediai.enterprise.feature.healthtimeline.domain.model.TimelineItem
import com.mediai.enterprise.feature.healthtimeline.domain.repository.TimelineRepository
import com.mediai.enterprise.feature.healthtimeline.domain.usecase.GetHealthTimelineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val getHealthTimelineUseCase: GetHealthTimelineUseCase,
    private val repository: TimelineRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimelineUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTimeline()
    }

    private fun loadTimeline() {
        getHealthTimelineUseCase()
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .onEach { items ->
                _uiState.update { it.copy(isLoading = false, items = items) }
                generateSummary(items)
            }
            .launchIn(viewModelScope)
    }

    private fun generateSummary(items: List<TimelineItem>) {
        if (items.isEmpty()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isGeneratingSummary = true) }
            val result = repository.getAiSummary(items.take(10)) // Summarize recent 10 items
            if (result.isSuccess) {
                _uiState.update { it.copy(isGeneratingSummary = false, summary = result.getOrNull()) }
            } else {
                _uiState.update { it.copy(isGeneratingSummary = false) }
            }
        }
    }
}

data class TimelineUiState(
    val items: List<TimelineItem> = emptyList(),
    val isLoading: Boolean = false,
    val summary: String? = null,
    val isGeneratingSummary: Boolean = false
)
