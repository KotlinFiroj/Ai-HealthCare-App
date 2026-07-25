package com.mediai.enterprise.core.analytics.di

import com.mediai.enterprise.core.analytics.AnalyticsHelper
import com.mediai.enterprise.core.analytics.FirebaseAnalyticsHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

    @Binds
    @Singleton
    abstract fun bindAnalyticsHelper(
        firebaseAnalyticsHelper: FirebaseAnalyticsHelper
    ): AnalyticsHelper
}
