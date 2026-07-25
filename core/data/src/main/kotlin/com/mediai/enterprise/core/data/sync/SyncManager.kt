package com.mediai.enterprise.core.data.sync

import android.content.Context
import androidx.work.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [SyncManager]
 * Interface to trigger and manage background data synchronization.
 */
@Singleton
class SyncManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    /**
     * Starts an immediate synchronization task.
     */
    fun triggerSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .addTag(SYNC_WORK_TAG)
            .build()

        workManager.enqueueUniqueWork(
            SYNC_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }

    /**
     * Schedules periodic synchronization (e.g., every 6 hours).
     */
    fun schedulePeriodicSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicSyncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            6, java.util.concurrent.TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .addTag(SYNC_WORK_TAG)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "periodic_$SYNC_WORK_NAME",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncRequest
        )
    }

    companion object {
        private const val SYNC_WORK_NAME = "MediAiSyncWork"
        private const val SYNC_WORK_TAG = "sync"
    }
}
