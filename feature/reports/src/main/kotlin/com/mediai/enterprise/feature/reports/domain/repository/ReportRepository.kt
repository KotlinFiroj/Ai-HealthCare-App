package com.mediai.enterprise.feature.reports.domain.repository

import android.graphics.Bitmap
import com.mediai.enterprise.feature.reports.domain.model.MedicalReport
import com.mediai.enterprise.feature.reports.domain.model.PrescriptionData
import com.mediai.enterprise.feature.reports.domain.model.ReportAnalysis
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun getReports(): Flow<List<MedicalReport>>
    suspend fun getReportById(id: String): MedicalReport?
    suspend fun uploadReport(title: String, category: String, filePath: String): Result<MedicalReport>
    suspend fun scanAndParsePrescription(bitmap: Bitmap): Result<PrescriptionData>
    suspend fun getReportAnalysis(reportId: String): Result<ReportAnalysis>
}
