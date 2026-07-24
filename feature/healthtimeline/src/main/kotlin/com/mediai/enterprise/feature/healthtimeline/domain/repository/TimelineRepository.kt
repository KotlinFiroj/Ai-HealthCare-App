package com.mediai.enterprise.feature.healthtimeline.domain.repository

import com.mediai.enterprise.feature.healthtimeline.domain.model.TimelineItem
import kotlinx.coroutines.flow.Flow

interface TimelineRepository {
    fun getTimelineItems(): Flow<List<TimelineItem>>
    suspend fun getAiSummary(items: List<TimelineItem>): Result<String>
}
