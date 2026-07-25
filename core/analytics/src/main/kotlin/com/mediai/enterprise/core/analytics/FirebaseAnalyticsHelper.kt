package com.mediai.enterprise.core.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [FirebaseAnalyticsHelper]
 * Implementation of [AnalyticsHelper] using Firebase Analytics.
 */
@Singleton
class FirebaseAnalyticsHelper @Inject constructor(
    @ApplicationContext context: Context
) : AnalyticsHelper {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun logEvent(name: String, params: Map<String, Any>) {
        val bundle = Bundle().apply {
            params.forEach { (key, value) ->
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Double -> putDouble(key, value)
                    is Boolean -> putBoolean(key, value)
                }
            }
        }
        firebaseAnalytics.logEvent(name, bundle)
    }
}
