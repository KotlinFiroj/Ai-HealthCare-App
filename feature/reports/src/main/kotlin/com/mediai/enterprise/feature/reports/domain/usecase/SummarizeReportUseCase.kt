package com.mediai.enterprise.feature.reports.domain.usecase

import com.mediai.enterprise.feature.reports.domain.model.ReportAnalysis
import com.mediai.enterprise.feature.reports.domain.repository.ReportRepository
import javax.inject.Inject

class SummarizeReportUseCase @Inject constructor(
    private val repository: ReportRepository
) {
    suspend operator fun invoke(reportId: String): Result<ReportAnalysis> {
        return repository.getReportAnalysis(reportId)
    }
}
