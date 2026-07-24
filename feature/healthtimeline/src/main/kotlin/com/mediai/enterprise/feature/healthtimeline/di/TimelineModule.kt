package com.mediai.enterprise.feature.healthtimeline.di

import com.mediai.enterprise.feature.healthtimeline.data.repository.TimelineRepositoryImpl
import com.mediai.enterprise.feature.healthtimeline.domain.repository.TimelineRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TimelineModule {

    @Binds
    @Singleton
    abstract fun bindTimelineRepository(
        timelineRepositoryImpl: TimelineRepositoryImpl
    ): TimelineRepository
}
