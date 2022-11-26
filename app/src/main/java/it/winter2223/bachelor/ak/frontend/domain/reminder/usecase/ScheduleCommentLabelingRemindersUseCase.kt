package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase

import it.winter2223.bachelor.ak.frontend.domain.reminder.model.ScheduleCommentLabelingRemindersResult

interface ScheduleCommentLabelingRemindersUseCase {
    operator fun invoke(hourOfDay: Int, minute: Int): ScheduleCommentLabelingRemindersResult
}
