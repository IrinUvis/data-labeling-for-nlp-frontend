package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import it.winter2223.bachelor.ak.frontend.data.reminder.worker.CommentLabelingReminderWorker
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.ReminderTime
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.ScheduleCommentLabelingRemindersResult
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.ScheduleCommentLabelingRemindersUseCase
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val HOURS_PER_DAY = 24L

class ProdScheduleCommentLabelingRemindersUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) : ScheduleCommentLabelingRemindersUseCase {
    override fun invoke(reminderTime: ReminderTime): ScheduleCommentLabelingRemindersResult {
        val workManager = WorkManager.getInstance(context)

        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, reminderTime.hour)
            set(Calendar.MINUTE, reminderTime.minute)
        }

        if (target.before(now)) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }

        val delayInMillis = target.timeInMillis - System.currentTimeMillis()

        val workRequest = PeriodicWorkRequest.Builder(
            CommentLabelingReminderWorker::class.java,
            HOURS_PER_DAY,
            TimeUnit.HOURS,
        ).setInitialDelay(
            delayInMillis,
            TimeUnit.MILLISECONDS,
        ).build()

        workManager.enqueueUniquePeriodicWork(
            CommentLabelingReminderWorker.UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest,
        )

        return ScheduleCommentLabelingRemindersResult.Success
    }
}
