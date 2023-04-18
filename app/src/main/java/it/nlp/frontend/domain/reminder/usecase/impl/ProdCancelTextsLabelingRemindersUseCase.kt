package it.nlp.frontend.domain.reminder.usecase.impl

import androidx.work.WorkManager
import it.nlp.frontend.data.local.reminder.worker.TextsLabelingReminderWorker
import it.nlp.frontend.domain.reminder.model.CancelTextsLabelingRemindersResult
import it.nlp.frontend.domain.reminder.usecase.CancelTextsLabelingRemindersUseCase
import javax.inject.Inject

class ProdCancelTextsLabelingRemindersUseCase @Inject constructor(
    private val workManager: WorkManager,
) : CancelTextsLabelingRemindersUseCase {
    override fun invoke(): CancelTextsLabelingRemindersResult {
        workManager.cancelUniqueWork(TextsLabelingReminderWorker.UNIQUE_WORK_NAME)

        return CancelTextsLabelingRemindersResult.Success
    }
}
