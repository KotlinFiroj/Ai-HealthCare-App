package com.mediai.enterprise.feature.appointment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediai.enterprise.feature.appointment.domain.model.*
import com.mediai.enterprise.feature.appointment.domain.usecase.BookAppointmentUseCase
import com.mediai.enterprise.feature.appointment.domain.usecase.SearchDoctorsUseCase
import com.mediai.enterprise.feature.appointment.domain.repository.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val searchDoctorsUseCase: SearchDoctorsUseCase,
    private val bookAppointmentUseCase: BookAppointmentUseCase,
    private val repository: AppointmentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppointmentUiState())
    val uiState = _uiState.asStateFlow()

    init {
        searchDoctors()
    }

    fun searchDoctors(query: String = "", specialization: String? = null) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = searchDoctorsUseCase(query, specialization)
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, doctors = result.getOrDefault(emptyList())) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message) }
            }
        }
    }

    fun selectDoctor(doctorId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = repository.getDoctorDetails(doctorId)
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, selectedDoctor = result.getOrNull()) }
                loadTimeSlots(doctorId, LocalDate.now())
            } else {
                _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message) }
            }
        }
    }

    fun loadTimeSlots(doctorId: String, date: LocalDate) {
        viewModelScope.launch {
            val result = repository.getTimeSlots(doctorId, date)
            if (result.isSuccess) {
                _uiState.update { it.copy(availableSlots = result.getOrDefault(emptyList())) }
            }
        }
    }

    fun selectSlot(slot: TimeSlot) {
        _uiState.update { it.copy(selectedSlot = slot) }
    }

    fun bookAppointment() {
        val doctorId = _uiState.value.selectedDoctor?.id ?: return
        val slot = _uiState.value.selectedSlot ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = bookAppointmentUseCase(doctorId, slot)
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, bookingSuccess = true) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message) }
            }
        }
    }
}

data class AppointmentUiState(
    val isLoading: Boolean = false,
    val doctors: List<Doctor> = emptyList(),
    val selectedDoctor: Doctor? = null,
    val availableSlots: List<TimeSlot> = emptyList(),
    val selectedSlot: TimeSlot? = null,
    val bookingSuccess: Boolean = false,
    val error: String? = null
)
