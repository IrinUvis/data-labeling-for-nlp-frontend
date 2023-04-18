package it.nlp.frontend.domain.reminder.usecase.impl

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import it.nlp.frontend.data.local.reminder.worker.TextsLabelingReminderWorker
import it.nlp.frontend.domain.reminder.model.ReminderTime
import it.nlp.frontend.domain.reminder.model.ScheduleTextsLabelingRemindersResult
import it.nlp.frontend.domain.reminder.usecase.ScheduleTextsLabelingRemindersUseCase
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProdScheduleTextsLabelingRemindersUseCase @Inject constructor(
    private val workManager: WorkManager,
) : ScheduleTextsLabelingRemindersUseCase {
    override fun invoke(
        reminderTime: ReminderTime,
        now: Calendar,
    ): ScheduleTextsLabelingRemindersResult {
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

        val workRequest = OneTimeWorkRequestBuilder<TextsLabelingReminderWorker>()
            .setInputData(
                workDataOf(
                    TextsLabelingReminderWorker.Companion.WorkDataKeys.HOUR to reminderTime.hour,
                    TextsLabelingReminderWorker.Companion.WorkDataKeys.MINUTE to reminderTime.minute
                ),
            )
            .setInitialDelay(
                delayInMillis,
                TimeUnit.MILLISECONDS,
            ).build()

        workManager.enqueueUniqueWork(
            TextsLabelingReminderWorker.UNIQUE_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            workRequest,
        )

        return ScheduleTextsLabelingRemindersResult.Success
    }
}
