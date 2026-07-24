package com.mediai.enterprise.feature.healthtimeline.data.repository

import com.mediai.enterprise.core.ai.HealthTimelineSummarizer
import com.mediai.enterprise.core.database.dao.HealthDao
import com.mediai.enterprise.core.database.dao.MedicineDao
import com.mediai.enterprise.feature.healthtimeline.domain.model.TimelineItem
import com.mediai.enterprise.feature.healthtimeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class TimelineRepositoryImpl @Inject constructor(
    private val healthDao: HealthDao,
    private val medicineDao: MedicineDao,
    private val summarizer: HealthTimelineSummarizer
) : TimelineRepository {

    override fun getTimelineItems(): Flow<List<TimelineItem>> {
        return combine(
            healthDao.getAllReports(),
            healthDao.getAllAppointments(),
            medicineDao.getAllMedicines()
        ) { reports, appts, meds ->
            val reportItems = reports.map {
                TimelineItem.Report(
                    id = it.id,
                    title = it.title,
                    category = it.category,
                    date = it.date.toLocalDateTime(),
                    summary = it.summary
                )
            }
            val apptItems = appts.map {
                TimelineItem.Appointment(
                    id = it.id,
                    doctorName = it.doctorName,
                    date = it.dateTime.toLocalDateTime(),
                    status = it.status,
                    type = it.type
                )
            }
            val medItems = meds.map {
                TimelineItem.Medication(
                    id = it.id,
                    name = it.name,
                    dosage = it.dosage,
                    date = it.startDate.toLocalDateTime()
                )
            }
            reportItems + apptItems + medItems
        }
    }

    override suspend fun getAiSummary(items: List<TimelineItem>): Result<String> {
        val eventsString = items.joinToString("\n") {
            when(it) {
                is TimelineItem.Report -> "Report: ${it.title} (${it.category}) on ${it.date}"
                is TimelineItem.Appointment -> "Appointment with ${it.doctorName} on ${it.date}"
                is TimelineItem.Medication -> "Medication: ${it.name} started on ${it.date}"
            }
        }
        return summarizer.summarizeTimeline(eventsString)
    }

    private fun Long.toLocalDateTime(): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
}
