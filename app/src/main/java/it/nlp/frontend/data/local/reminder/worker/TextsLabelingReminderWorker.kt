package it.nlp.frontend.data.local.reminder.worker

import android.content.Context
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import it.nlp.frontend.util.reminder.TextsLabelingNotificationHandler
import java.util.Calendar
import java.util.concurrent.TimeUnit

class TextsLabelingReminderWorker(
    private val context: Context,
    workerParameters: WorkerParameters,
) : Worker(context, workerParameters) {
    companion object {
        private const val TAG = "TextsLabelingReminderW"
        const val UNIQUE_WORK_NAME = "texts_labeling_reminder_worker"
        object WorkDataKeys {
            const val HOUR = "HOUR"
            const val MINUTE = "MINUTE"
        }
    }

    override fun doWork(): Result {
        Log.d(TAG, "doWork: called")
        val workManager = WorkManager.getInstance(context)
        if (TextsLabelingNotificationHandler.canSendNotifications(context)) {
            TextsLabelingNotificationHandler.createTextLabelingReminderNotification(context)

            // These should always be there.
            val hour = inputData.getInt(WorkDataKeys.HOUR, 0)
            val minute = inputData.getInt(WorkDataKeys.MINUTE, 0)

            val now = Calendar.getInstance().apply {
                // add one minute to ensure that it doesn't fire instantaneously
                add(Calendar.MINUTE, 1)
            }
            val target = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            if (target.before(now)) {
                target.add(Calendar.DAY_OF_YEAR, 1)
            }

            val delayInMillis = target.timeInMillis - System.currentTimeMillis()

            val workRequest = OneTimeWorkRequestBuilder<TextsLabelingReminderWorker>()
                .setInputData(
                    workDataOf(
                        WorkDataKeys.HOUR to hour,
                        WorkDataKeys.MINUTE to minute
                    ),
                )
                .setInitialDelay(
                    delayInMillis,
                    TimeUnit.MILLISECONDS,
                ).build()

            workManager.enqueueUniqueWork(
                UNIQUE_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        }

        return Result.success()
    }
}
