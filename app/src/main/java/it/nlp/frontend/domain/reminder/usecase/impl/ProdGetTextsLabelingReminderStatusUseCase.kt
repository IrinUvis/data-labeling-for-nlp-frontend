package it.nlp.frontend.domain.reminder.usecase.impl

import androidx.work.WorkInfo
import androidx.work.WorkManager
import it.nlp.frontend.data.local.reminder.worker.TextsLabelingReminderWorker
import it.nlp.frontend.domain.reminder.model.GetTextsLabelingReminderStatusResult
import it.nlp.frontend.domain.reminder.usecase.GetTextsLabelingReminderStatusUseCase
import javax.inject.Inject

class ProdGetTextsLabelingReminderStatusUseCase @Inject constructor(
    private val workManager: WorkManager,
) : GetTextsLabelingReminderStatusUseCase {

    override fun invoke(): GetTextsLabelingReminderStatusResult {
        val workInfo =
            workManager.getWorkInfosForUniqueWork(TextsLabelingReminderWorker.UNIQUE_WORK_NAME)
                .get().getOrNull(0)

        return workInfo?.let { info ->
            val state = info.state
            val isScheduled = (state == WorkInfo.State.ENQUEUED || state == WorkInfo.State.RUNNING)
            GetTextsLabelingReminderStatusResult.Success(
                isScheduled = isScheduled,
            )
        } ?: GetTextsLabelingReminderStatusResult.Success(
            isScheduled = false,
        )

    }
}
