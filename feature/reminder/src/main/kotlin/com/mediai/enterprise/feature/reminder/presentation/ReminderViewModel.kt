package com.mediai.enterprise.feature.reminder.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediai.enterprise.core.database.dao.MedicineDao
import com.mediai.enterprise.core.database.entity.MedicineEntity
import com.mediai.enterprise.feature.reminder.service.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val medicineDao: MedicineDao,
    private val scheduler: ReminderScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReminderUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMedicines()
    }

    private fun loadMedicines() {
        medicineDao.getAllMedicines()
            .onEach { medicines ->
                _uiState.update { it.copy(medicines = medicines) }
            }
            .launchIn(viewModelScope)
    }

    fun addMedicine(name: String, dosage: String, frequency: String, reminderTimes: List<String>) {
        viewModelScope.launch {
            val entity = MedicineEntity(
                name = name,
                dosage = dosage,
                frequency = frequency,
                reminderTimes = reminderTimes.joinToString(","),
                startDate = System.currentTimeMillis(),
                endDate = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000) // Default 1 week
            )
            val id = medicineDao.insertMedicine(entity)

            // Schedule reminders
            reminderTimes.forEach { time ->
                scheduler.scheduleReminder(id, name, dosage, time)
            }
        }
    }
}

data class ReminderUiState(
    val medicines: List<MedicineEntity> = emptyList(),
    val isLoading: Boolean = false
)
