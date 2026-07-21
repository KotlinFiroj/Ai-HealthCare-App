package com.mediai.enterprise.feature.appointment.domain.usecase

import com.mediai.enterprise.feature.appointment.domain.model.Doctor
import com.mediai.enterprise.feature.appointment.domain.repository.AppointmentRepository
import javax.inject.Inject

class SearchDoctorsUseCase @Inject constructor(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(query: String = "", specialization: String? = null): Result<List<Doctor>> {
        return repository.searchDoctors(query, specialization)
    }
}
