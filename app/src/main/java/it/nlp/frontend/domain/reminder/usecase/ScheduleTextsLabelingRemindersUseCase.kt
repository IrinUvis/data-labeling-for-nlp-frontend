package it.nlp.frontend.domain.reminder.usecase

import it.nlp.frontend.domain.reminder.model.ReminderTime
import it.nlp.frontend.domain.reminder.model.ScheduleTextsLabelingRemindersResult
import java.util.Calendar

interface ScheduleTextsLabelingRemindersUseCase {
    operator fun invoke(
        reminderTime: ReminderTime,
        now: Calendar,
    ): ScheduleTextsLabelingRemindersResult
}
