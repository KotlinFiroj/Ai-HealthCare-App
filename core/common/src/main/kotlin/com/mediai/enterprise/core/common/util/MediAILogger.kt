package com.mediai.enterprise.core.common.util

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

/**
 * [MediAILogger]
 * Configures Timber for structured logging.
 * In production, errors are also sent to Firebase Crashlytics.
 */
object MediAILogger {
    fun init(isDebug: Boolean) {
        if (isDebug) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }
}

/**
 * [CrashlyticsTree]
 * A Timber Tree that sends logs to Firebase Crashlytics in production.
 */
private class CrashlyticsTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return
        }

        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.log(message)

        if (t != null) {
            crashlytics.recordException(t)
        }
    }
}
