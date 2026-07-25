package com.mediai.enterprise.feature.home.data.remote

import com.mediai.enterprise.feature.home.data.remote.model.HealthSummaryDto
import com.mediai.enterprise.feature.home.data.remote.model.TrendDataDto
import retrofit2.http.GET

interface HomeApiService {
    @GET("analytics/stats")
    suspend fun getHealthSummary(): HealthSummaryDto

    @GET("analytics/trends")
    suspend fun getHealthTrends(): List<TrendDataDto>
}
