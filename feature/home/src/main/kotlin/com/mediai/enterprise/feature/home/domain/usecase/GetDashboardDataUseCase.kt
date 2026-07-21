package com.mediai.enterprise.feature.home.domain.usecase

import com.mediai.enterprise.feature.home.domain.model.DashboardData
import com.mediai.enterprise.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDashboardDataUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(): Flow<Result<DashboardData>> = repository.getDashboardData()
}
