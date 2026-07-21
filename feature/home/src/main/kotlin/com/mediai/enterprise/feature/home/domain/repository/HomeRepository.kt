package com.mediai.enterprise.feature.home.domain.repository

import com.mediai.enterprise.feature.home.domain.model.DashboardData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getDashboardData(): Flow<Result<DashboardData>>
}
