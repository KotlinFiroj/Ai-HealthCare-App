package com.mediai.enterprise.feature.reminder.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mediai.enterprise.core.common.util.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MedicineReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val medicineName = inputData.getString(KEY_MEDICINE_NAME) ?: "Medication"
        val dosage = inputData.getString(KEY_DOSAGE) ?: ""

        notificationHelper.showMedicineNotification(
            id = id.hashCode(),
            title = "Time for your medicine",
            message = "Take $medicineName ($dosage)"
        )

        return Result.success()
    }

    companion object {
        const val KEY_MEDICINE_NAME = "medicine_name"
        const val KEY_DOSAGE = "dosage"
    }
}
