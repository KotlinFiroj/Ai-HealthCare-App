package com.mediai.enterprise.feature.reports.domain.model

data class ReportAnalysis(
    val summary: String,
    val riskFactors: List<String>,
    val suggestedQuestions: List<String>,
    val confidenceScore: Float
)
