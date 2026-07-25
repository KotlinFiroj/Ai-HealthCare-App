package com.mediai.enterprise.feature.reports.data.remote

import com.mediai.enterprise.feature.reports.data.remote.model.ReportRequestDto
import com.mediai.enterprise.feature.reports.data.remote.model.ReportResponseDto
import retrofit2.http.*

interface ReportApiService {
    @POST("reports/")
    suspend fun uploadReport(@Body request: ReportRequestDto): ReportResponseDto

    @GET("reports/")
    suspend fun getReports(): List<ReportResponseDto>

    @GET("reports/{id}")
    suspend fun getReport(@Path("id") id: String): ReportResponseDto
}
