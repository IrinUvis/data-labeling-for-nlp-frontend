package it.nlp.frontend.domain.reminder.usecase.impl

import androidx.work.WorkManager
import it.nlp.frontend.data.local.reminder.worker.CommentLabelingReminderWorker
import it.nlp.frontend.domain.reminder.model.CancelCommentLabelingRemindersResult
import it.nlp.frontend.domain.reminder.usecase.CancelCommentLabelingRemindersUseCase
import javax.inject.Inject

class ProdCancelCommentLabelingRemindersUseCase @Inject constructor(
    private val workManager: WorkManager,
) : CancelCommentLabelingRemindersUseCase {
    override fun invoke(): CancelCommentLabelingRemindersResult {
        workManager.cancelUniqueWork(CommentLabelingReminderWorker.UNIQUE_WORK_NAME)

        return CancelCommentLabelingRemindersResult.Success
    }
}
