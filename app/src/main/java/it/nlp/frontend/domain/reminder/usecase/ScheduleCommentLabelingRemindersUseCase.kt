package it.nlp.frontend.domain.reminder.usecase

import it.nlp.frontend.domain.reminder.model.ReminderTime
import it.nlp.frontend.domain.reminder.model.ScheduleCommentLabelingRemindersResult
import java.util.Calendar

interface ScheduleCommentLabelingRemindersUseCase {
    operator fun invoke(
        reminderTime: ReminderTime,
        now: Calendar,
    ): ScheduleCommentLabelingRemindersResult
}
