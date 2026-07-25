package com.mediai.enterprise.core.analytics

/**
 * [AnalyticsHelper]
 * Interface for logging events across the application.
 */
interface AnalyticsHelper {
    fun logEvent(name: String, params: Map<String, Any> = emptyMap())
}
