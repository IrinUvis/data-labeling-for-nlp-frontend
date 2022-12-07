package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.Operation
import androidx.work.WorkManager
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.ReminderTime
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.ScheduleCommentLabelingRemindersResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.util.Calendar

@OptIn(ExperimentalCoroutinesApi::class)
class ProdScheduleCommentLabelingRemindersUseCaseTest {

    @Test
    fun useCase_invoked_returnsSuccess() = runTest {
        val nowHour = 8
        val nowMinute = 30
        val reminderHour = 8
        val reminderMinute = 0
        val reminderTime = ReminderTime(
            hour = reminderHour,
            minute = reminderMinute
        )
        val now = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, nowHour)
            set(Calendar.MINUTE, nowMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val operation: Operation = mockk()
        val workManagerMock: WorkManager = mockk()

        every {
            workManagerMock.enqueueUniqueWork(
                any(),
                ExistingWorkPolicy.REPLACE,
                any<OneTimeWorkRequest>(),
            )
        } returns operation

        val useCase = ProdScheduleCommentLabelingRemindersUseCase(
            workManager = workManagerMock,
        )

        val expectedResult = ScheduleCommentLabelingRemindersResult.Success

        val result = useCase(
            reminderTime = reminderTime,
            now = now,
        )

        Truth.assertThat(result).isEqualTo(expectedResult)
    }
}
