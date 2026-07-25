package com.mediai.enterprise.feature.ai.domain.usecase

import com.mediai.enterprise.feature.ai.domain.model.SymptomAssessment
import com.mediai.enterprise.feature.ai.domain.repository.AiRepository
import javax.inject.Inject

class CheckSymptomsUseCase @Inject constructor(
    private val repository: AiRepository
) {
    suspend operator fun invoke(symptoms: String): Result<SymptomAssessment> {
        if (symptoms.isBlank()) return Result.failure(Exception("Please enter your symptoms"))
        return repository.assessSymptoms(symptoms)
    }
}
