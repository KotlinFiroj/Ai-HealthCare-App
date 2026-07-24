package com.mediai.enterprise.feature.emergency.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediai.enterprise.core.database.dao.EmergencyDao
import com.mediai.enterprise.core.database.entity.EmergencyContactEntity
import com.mediai.enterprise.core.database.entity.MedicalProfileEntity
import com.mediai.enterprise.feature.emergency.service.SosService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyViewModel @Inject constructor(
    private val sosService: SosService,
    private val emergencyDao: EmergencyDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmergencyUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        // Mock userId for now
        val userId = "user123"

        emergencyDao.getEmergencyContacts()
            .onEach { contacts ->
                _uiState.update { it.copy(contacts = contacts) }
            }.launchIn(viewModelScope)

        emergencyDao.getMedicalProfile(userId)
            .onEach { profile ->
                _uiState.update { it.copy(medicalProfile = profile) }
            }.launchIn(viewModelScope)
    }

    fun triggerSos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSosTriggering = true) }
            sosService.triggerSos()
            _uiState.update { it.copy(isSosTriggering = false, sosSent = true) }
        }
    }

    fun addContact(name: String, phone: String) {
        viewModelScope.launch {
            emergencyDao.insertContact(EmergencyContactEntity(name = name, phoneNumber = phone))
        }
    }
}

data class EmergencyUiState(
    val contacts: List<EmergencyContactEntity> = emptyList(),
    val medicalProfile: MedicalProfileEntity? = null,
    val isSosTriggering: Boolean = false,
    val sosSent: Boolean = false
)
