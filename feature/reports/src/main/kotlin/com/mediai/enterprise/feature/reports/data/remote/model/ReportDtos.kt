package com.mediai.enterprise.feature.reports.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ReportRequestDto(
    val title: String,
    val category: String? = null,
    val file_url: String,
    val date: String? = null
)

@Serializable
data class ReportResponseDto(
    val id: String,
    val title: String,
    val category: String? = null,
    val date: String? = null,
    val file_url: String,
    val ai_summary: String? = null
)
