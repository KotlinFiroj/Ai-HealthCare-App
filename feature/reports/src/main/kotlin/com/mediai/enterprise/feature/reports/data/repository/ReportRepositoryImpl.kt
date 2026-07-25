package com.mediai.enterprise.feature.reports.data.repository

import android.graphics.Bitmap
import com.mediai.enterprise.core.ai.MedicalAiParser
import com.mediai.enterprise.core.ai.MedicalOcrAnalyzer
import com.mediai.enterprise.core.ai.MedicalReportSummarizer
import com.mediai.enterprise.feature.reports.domain.model.*
import com.mediai.enterprise.feature.reports.domain.repository.ReportRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val ocrAnalyzer: MedicalOcrAnalyzer,
    private val aiParser: MedicalAiParser,
    private val reportSummarizer: MedicalReportSummarizer
) : ReportRepository {

    private val mockReports = listOf(
        MedicalReport(
            id = "1",
            title = "Annual Blood Test",
            category = ReportCategory.BLOOD_TEST,
            date = LocalDateTime.now().minusDays(10),
            fileUrl = "mock_url_1",
            summary = "All levels are within normal range."
        ),
        MedicalReport(
            id = "2",
            title = "Knee X-Ray",
            category = ReportCategory.X_RAY,
            date = LocalDateTime.now().minusMonths(2),
            fileUrl = "mock_url_2",
            summary = "No fracture detected."
        )
    )

    override fun getReports(): Flow<List<MedicalReport>> = flowOf(mockReports)

    override suspend fun getReportById(id: String): MedicalReport? {
        return mockReports.find { it.id == id }
    }

    override suspend fun uploadReport(title: String, category: String, filePath: String): Result<MedicalReport> {
        delay(1000) // Mock upload
        return Result.success(
            MedicalReport(
                id = UUID.randomUUID().toString(),
                title = title,
                category = ReportCategory.valueOf(category),
                date = LocalDateTime.now(),
                fileUrl = filePath,
                syncStatus = SyncStatus.SYNCED
            )
        )
    }

    override suspend fun scanAndParsePrescription(bitmap: Bitmap): Result<PrescriptionData> {
        // 1. OCR
        val ocrResult = ocrAnalyzer.extractText(bitmap).getOrElse { return Result.failure(it) }

        // 2. AI Parsing
        val aiResult = aiParser.parsePrescription(ocrResult).getOrElse { return Result.failure(it) }

        // 3. Transform JSON string to PrescriptionData
        return try {
            // In a real app, use kotlinx.serialization to parse the JSON from Gemini
            // For now, returning a mock based on the expectation
            Result.success(
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
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getReportAnalysis(reportId: String): Result<ReportAnalysis> {
        val report = getReportById(reportId) ?: return Result.failure(Exception("Report not found"))

        // Simulating AI analysis flow
        delay(1500)

        return Result.success(
            ReportAnalysis(
                summary = "Your blood test results show normal hemoglobin levels but slightly elevated cholesterol. This is a common finding and can often be managed through diet and lifestyle changes. Disclaimer: This AI-generated summary is for informational purposes and not a substitute for professional medical advice.",
                riskFactors = listOf("Elevated LDL Cholesterol (140 mg/dL)", "Borderline High Triglycerides"),
                suggestedQuestions = listOf(
                    "What lifestyle changes do you recommend to lower my LDL cholesterol?",
                    "Should I repeat this test in 3 or 6 months?",
                    "Are there any specific foods I should avoid?"
                ),
                confidenceScore = 0.92f
            )
        )
    }
}
