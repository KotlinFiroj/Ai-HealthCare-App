package com.mediai.enterprise.feature.reminder.service

import android.content.Context
import androidx.work.*
import com.mediai.enterprise.feature.reminder.worker.MedicineReminderWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleReminder(medicineId: Long, name: String, dosage: String, time: String) {
        val (hour, minute) = time.split(":").map { it.toInt() }
        val now = LocalDateTime.now()
        var scheduledTime = now.withHour(hour).withMinute(minute).withSecond(0)

        if (scheduledTime.isBefore(now)) {
            scheduledTime = scheduledTime.plusDays(1)
        }

        val delay = Duration.between(now, scheduledTime)

        val data = Data.Builder()
            .putString(MedicineReminderWorker.KEY_MEDICINE_NAME, name)
            .putString(MedicineReminderWorker.KEY_DOSAGE, dosage)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<MedicineReminderWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(delay.toMillis(), TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag("medicine_$medicineId")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "medicine_$medicineId",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    fun cancelReminder(medicineId: Long) {
        workManager.cancelUniqueWork("medicine_$medicineId")
    }
}
