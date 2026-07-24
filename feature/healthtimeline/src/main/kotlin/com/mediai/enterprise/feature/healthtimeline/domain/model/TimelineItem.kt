package com.mediai.enterprise.feature.healthtimeline.domain.model

import java.time.LocalDateTime

sealed class TimelineItem(val dateTime: LocalDateTime) {
    data class Report(
        val id: String,
        val title: String,
        val category: String,
        val date: LocalDateTime,
        val summary: String?
    ) : TimelineItem(date)

    data class Appointment(
        val id: String,
        val doctorName: String,
        val date: LocalDateTime,
        val status: String,
        val type: String
    ) : TimelineItem(date)

    data class Medication(
        val id: Long,
        val name: String,
        val dosage: String,
        val date: LocalDateTime
    ) : TimelineItem(date)
}
