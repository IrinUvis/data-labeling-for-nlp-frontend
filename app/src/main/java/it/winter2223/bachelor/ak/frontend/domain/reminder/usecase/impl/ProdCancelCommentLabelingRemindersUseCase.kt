package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl

import android.content.Context
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import it.winter2223.bachelor.ak.frontend.data.reminder.worker.CommentLabelingReminderWorker
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.CancelCommentLabelingRemindersResult
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.CancelCommentLabelingRemindersUseCase
import javax.inject.Inject

class ProdCancelCommentLabelingRemindersUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) : CancelCommentLabelingRemindersUseCase {
    override fun invoke(): CancelCommentLabelingRemindersResult {
        val workManager = WorkManager.getInstance(context)

        workManager.cancelUniqueWork(CommentLabelingReminderWorker.UNIQUE_WORK_NAME)

        return CancelCommentLabelingRemindersResult.Success
    }
}
