package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase

import it.winter2223.bachelor.ak.frontend.domain.reminder.model.ReminderTime
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.ScheduleCommentLabelingRemindersResult
import java.util.Calendar

interface ScheduleCommentLabelingRemindersUseCase {
    operator fun invoke(
        reminderTime: ReminderTime,
        now: Calendar,
    ): ScheduleCommentLabelingRemindersResult
}
