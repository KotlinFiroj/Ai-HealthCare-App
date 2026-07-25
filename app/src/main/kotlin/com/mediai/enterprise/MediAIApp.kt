package com.mediai.enterprise

import android.app.Application
import com.mediai.enterprise.core.analytics.RemoteConfigManager
import com.mediai.enterprise.core.common.util.MediAILogger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * [MediAIApp]
 * The [Application] class for the MediAI Enterprise platform.
 * Initializes Hilt for Dependency Injection and observability tools.
 */
@HiltAndroidApp
class MediAIApp : Application() {

    @Inject
    lateinit var remoteConfigManager: RemoteConfigManager

    override fun onCreate() {
        super.onCreate()

        // Initialize Logging
        MediAILogger.init(BuildConfig.DEBUG)

        // Initialize Remote Config
        remoteConfigManager.fetchAndActivate()
    }
}
