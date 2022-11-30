package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl

import android.util.Log
import it.winter2223.bachelor.ak.frontend.data.local.reminder.repository.ReminderTimeRepository
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.GetReminderTimeFlowResult
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.ReminderTime
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.GetReminderTimeFlowUseCase
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class ProdGetReminderTimeFlowUseCase @Inject constructor(
    private val reminderTimeRepository: ReminderTimeRepository,
) : GetReminderTimeFlowUseCase {
    companion object {
        private const val TAG = "ProfGetReminderTimeFlowUC"
    }

    override suspend fun invoke(): GetReminderTimeFlowResult {
        return try {
            val reminderTimeFlow = reminderTimeRepository.reminderTimeFlow().map { reminderPreferences ->
                reminderPreferences?.let {
                    ReminderTime(
                        hour = it.hour,
                        minute = it.minute,
                    )
                }
            }

            GetReminderTimeFlowResult.Success(
                reminderTimeFlow = reminderTimeFlow,
            )
        } catch (e: IOException) {
            Log.e(TAG, "An exception has occurred while reading reminder time flow", e)
            GetReminderTimeFlowResult.Failure
        }
    }
}
