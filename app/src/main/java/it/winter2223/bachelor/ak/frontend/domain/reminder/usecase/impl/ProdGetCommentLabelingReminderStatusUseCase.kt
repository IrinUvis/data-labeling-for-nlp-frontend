package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl

import androidx.work.WorkInfo
import androidx.work.WorkManager
import it.winter2223.bachelor.ak.frontend.data.local.reminder.worker.CommentLabelingReminderWorker
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.GetCommentLabelingReminderStatusResult
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.GetCommentLabelingReminderStatusUseCase
import javax.inject.Inject

class ProdGetCommentLabelingReminderStatusUseCase @Inject constructor(
    private val workManager: WorkManager,
) : GetCommentLabelingReminderStatusUseCase {

    override fun invoke(): GetCommentLabelingReminderStatusResult {
        val workInfo =
            workManager.getWorkInfosForUniqueWork(CommentLabelingReminderWorker.UNIQUE_WORK_NAME)
                .get().getOrNull(0)

        return workInfo?.let { info ->
            val state = info.state
            val isScheduled = (state == WorkInfo.State.ENQUEUED || state == WorkInfo.State.RUNNING)
            GetCommentLabelingReminderStatusResult.Success(
                isScheduled = isScheduled,
            )
        } ?: GetCommentLabelingReminderStatusResult.Success(
            isScheduled = false,
        )

    }
}
