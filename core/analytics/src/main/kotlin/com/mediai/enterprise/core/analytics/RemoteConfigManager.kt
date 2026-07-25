package com.mediai.enterprise.core.analytics

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [RemoteConfigManager]
 * Manages fetching and providing feature flags from Firebase Remote Config.
 */
@Singleton
class RemoteConfigManager @Inject constructor() {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600 // Fetch every hour
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf(
            IS_AI_COACH_ENABLED to true,
            IS_SYMPTOM_CHECKER_ENABLED to true
        ))
    }

    /**
     * Fetches the latest configuration from the server.
     */
    fun fetchAndActivate() {
        remoteConfig.fetchAndActivate()
    }

    /**
     * Checks if the AI Health Coach feature is enabled.
     */
    fun isAiCoachEnabled(): Boolean {
        return remoteConfig.getBoolean(IS_AI_COACH_ENABLED)
    }

    companion object {
        private const val IS_AI_COACH_ENABLED = "is_ai_coach_enabled"
        private const val IS_SYMPTOM_CHECKER_ENABLED = "is_symptom_checker_enabled"
    }
}
