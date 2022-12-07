package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import it.winter2223.bachelor.ak.frontend.data.local.reminder.worker.CommentLabelingReminderWorker
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.ReminderTime
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.ScheduleCommentLabelingRemindersResult
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.ScheduleCommentLabelingRemindersUseCase
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProdScheduleCommentLabelingRemindersUseCase @Inject constructor(
    private val workManager: WorkManager,
) : ScheduleCommentLabelingRemindersUseCase {
    override fun invoke(
        reminderTime: ReminderTime,
        now: Calendar,
    ): ScheduleCommentLabelingRemindersResult {
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, reminderTime.hour)
            set(Calendar.MINUTE, reminderTime.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (target.before(now)) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }

        val delayInMillis = target.timeInMillis - now.timeInMillis

        val workRequest = OneTimeWorkRequestBuilder<CommentLabelingReminderWorker>()
            .setInputData(
                workDataOf(
                    CommentLabelingReminderWorker.Companion.WorkDataKeys.HOUR to reminderTime.hour,
                    CommentLabelingReminderWorker.Companion.WorkDataKeys.MINUTE to reminderTime.minute
                ),
            )
            .setInitialDelay(
                delayInMillis,
                TimeUnit.MILLISECONDS,
            ).build()

        workManager.enqueueUniqueWork(
            CommentLabelingReminderWorker.UNIQUE_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            workRequest,
        )

        return ScheduleCommentLabelingRemindersResult.Success
    }
}
