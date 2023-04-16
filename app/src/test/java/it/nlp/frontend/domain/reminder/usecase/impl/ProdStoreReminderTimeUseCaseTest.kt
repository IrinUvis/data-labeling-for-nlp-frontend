package it.nlp.frontend.domain.reminder.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.local.reminder.model.ReminderTimePreferences
import it.nlp.frontend.data.local.reminder.repository.ReminderTimeRepository
import it.nlp.frontend.domain.reminder.model.ReminderTime
import it.nlp.frontend.domain.reminder.model.StoreReminderTimeResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdStoreReminderTimeUseCaseTest {

    @Test
    fun useCase_invokedWithRepositoryThrowingIOException_returnsFailure() = runTest {
        val hour = 8
        val minute = 30
        val reminderTime = ReminderTime(
            hour = hour,
            minute = minute,
        )
        val reminderTimePreferences = ReminderTimePreferences(
            hour = hour,
            minute = minute,
        )

        val reminderTimeRepository: ReminderTimeRepository = mockk()

        coEvery { reminderTimeRepository.storeReminderTime(reminderTimePreferences) } throws IOException()

        val useCase = ProdStoreReminderTimeUseCase(
            reminderTimeRepository = reminderTimeRepository,
        )


        val result = useCase(reminderTime) as StoreReminderTimeResult.Failure

        Truth.assertThat(result.e).isInstanceOf(IOException::class.java)
    }

    @Test
    fun useCase_invokedWithRepositoryCallSucceeding_returnsSuccess() = runTest {
        val hour = 8
        val minute = 30
        val reminderTime = ReminderTime(
            hour = hour,
            minute = minute,
        )
        val reminderTimePreferences = ReminderTimePreferences(
            hour = hour,
            minute = minute,
        )

        val reminderTimeRepository: ReminderTimeRepository = mockk()

        coEvery { reminderTimeRepository.storeReminderTime(reminderTimePreferences) } returns Unit

        val useCase = ProdStoreReminderTimeUseCase(
            reminderTimeRepository = reminderTimeRepository,
        )

        val expectedResult = StoreReminderTimeResult.Success

        val result = useCase(reminderTime)

        Truth.assertThat(result).isEqualTo(expectedResult)
    }
}
