package it.winter2223.bachelor.ak.frontend.data.reminder.worker

import android.content.Context
import android.util.Log
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import it.winter2223.bachelor.ak.frontend.util.reminder.CommentLabelingNotificationHandler

class CommentLabelingReminderWorker(
    private val context: Context,
    workerParameters: WorkerParameters,
) : Worker(context, workerParameters) {
    companion object {
        private const val TAG = "CommentLabelingReminderW"
        const val UNIQUE_WORK_NAME = "comment_labeling_reminder_worker"
    }

    override fun doWork(): Result {
        Log.d(TAG, "doWork called")
        if (CommentLabelingNotificationHandler.canSendNotifications(context)) {
            CommentLabelingNotificationHandler.createCommentLabelingReminderNotification(context)
        } else {
            val workManager = WorkManager.getInstance(context)
            workManager.cancelUniqueWork(UNIQUE_WORK_NAME)
        }
        return Result.success()
    }
}
