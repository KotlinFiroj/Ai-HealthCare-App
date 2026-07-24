package com.mediai.enterprise.feature.healthtimeline.domain.usecase

import com.mediai.enterprise.feature.healthtimeline.domain.model.TimelineItem
import com.mediai.enterprise.feature.healthtimeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHealthTimelineUseCase @Inject constructor(
    private val repository: TimelineRepository
) {
    operator fun invoke(): Flow<List<TimelineItem>> {
        return repository.getTimelineItems().map { items ->
            items.sortedByDescending { it.dateTime }
        }
    }
}
