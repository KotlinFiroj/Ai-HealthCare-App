package com.mediai.enterprise.feature.ai.data.remote

import com.mediai.enterprise.feature.ai.data.remote.model.RiskRequestDto
import com.mediai.enterprise.feature.ai.data.remote.model.RiskResponseDto
import com.mediai.enterprise.feature.ai.data.remote.model.SymptomRequestDto
import com.mediai.enterprise.feature.ai.data.remote.model.SymptomResponseDto
import retrofit2.http.*

interface AiApiService {
    @POST("ai/symptoms")
    suspend fun assessSymptoms(@Body request: SymptomRequestDto): SymptomResponseDto

    @POST("ai/risks")
    suspend fun predictRisks(@Body request: RiskRequestDto): List<RiskResponseDto>
}
