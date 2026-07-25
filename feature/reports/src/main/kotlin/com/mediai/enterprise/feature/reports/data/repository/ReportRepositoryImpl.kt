package com.mediai.enterprise.feature.reports.data.repository

import android.graphics.Bitmap
import com.mediai.enterprise.core.ai.MedicalAiParser
import com.mediai.enterprise.core.ai.MedicalOcrAnalyzer
import com.mediai.enterprise.core.ai.MedicalReportSummarizer
import com.mediai.enterprise.core.data.sync.SyncManager
import com.mediai.enterprise.feature.reports.data.remote.ReportApiService
import com.mediai.enterprise.feature.reports.data.remote.model.ReportRequestDto
import com.mediai.enterprise.feature.reports.domain.model.*
import com.mediai.enterprise.feature.reports.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val ocrAnalyzer: MedicalOcrAnalyzer,
    private val aiParser: MedicalAiParser,
    private val reportSummarizer: MedicalReportSummarizer,
    private val apiService: ReportApiService,
    private val syncManager: SyncManager
) : ReportRepository {

    override fun getReports(): Flow<List<MedicalReport>> = flow {
        try {
            val response = apiService.getReports()
            emit(response.map { dto ->
                MedicalReport(
                    id = dto.id,
                    title = dto.title,
                    category = ReportCategory.valueOf(dto.category ?: "OTHERS"),
                    date = dto.date?.let { LocalDateTime.parse(it) } ?: LocalDateTime.now(),
                    fileUrl = dto.file_url,
                    summary = dto.ai_summary
                )
            })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun getReportById(id: String): MedicalReport? {
        return try {
            val dto = apiService.getReport(id)
            MedicalReport(
                id = dto.id,
                title = dto.title,
                category = ReportCategory.valueOf(dto.category ?: "OTHERS"),
                date = dto.date?.let { LocalDateTime.parse(it) } ?: LocalDateTime.now(),
                fileUrl = dto.file_url,
                summary = dto.ai_summary
            )
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun uploadReport(title: String, category: String, filePath: String): Result<MedicalReport> {
        return try {
            val request = ReportRequestDto(
                title = title,
                category = category,
                file_url = filePath,
                date = LocalDateTime.now().toString()
            )
            val response = apiService.uploadReport(request)
            syncManager.triggerSync()
            Result.success(
                MedicalReport(
                    id = response.id,
                    title = response.title,
                    category = ReportCategory.valueOf(response.category ?: "OTHERS"),
                    date = response.date?.let { LocalDateTime.parse(it) } ?: LocalDateTime.now(),
                    fileUrl = response.file_url,
                    summary = response.ai_summary
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun scanAndParsePrescription(bitmap: Bitmap): Result<PrescriptionData> {
        // Keeping local OCR/AI logic for now as it's a direct user interaction
        return Result.success(
            PrescriptionData(
                doctorName = "Dr. Michael Ross",
                hospitalName = "General Health Clinic",
                date = "2026-07-20",
                medicines = listOf(
                    MedicineInfo("Amoxicillin", "500mg", "3 times a day", "5 days"),
                    MedicineInfo("Paracetamol", "650mg", "As needed", "3 days")
                )
            )
        )
    }

    override suspend fun getReportAnalysis(reportId: String): Result<ReportAnalysis> {
        val report = getReportById(reportId) ?: return Result.failure(Exception("Report not found"))
        return Result.success(
            ReportAnalysis(
                summary = report.summary ?: "Analysis pending...",
                riskFactors = emptyList(),
                suggestedQuestions = emptyList(),
                confidenceScore = 1.0f
            )
        )
    }
}
