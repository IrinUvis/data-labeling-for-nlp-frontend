package it.nlp.frontend.domain.reminder.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.local.reminder.model.ReminderTimePreferences
import it.nlp.frontend.data.local.reminder.repository.ReminderTimeRepository
import it.nlp.frontend.domain.reminder.model.GetReminderTimeFlowResult
import it.nlp.frontend.domain.reminder.model.ReminderTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdGetReminderTimeFlowUseCaseTest {

    @Test
    fun useCase_invokedWithRepositoryThrowingIOException_returnsFailure() = runTest {
        val reminderTimeRepository: ReminderTimeRepository = mockk()

        coEvery { reminderTimeRepository.reminderTimeFlow() } throws IOException()

        val useCase = ProdGetReminderTimeFlowUseCase(
            reminderTimeRepository = reminderTimeRepository,
        )

        val result = useCase() as GetReminderTimeFlowResult.Failure

        Truth.assertThat(result.e).isInstanceOf(IOException::class.java)
    }

    @Test
    fun useCase_invokedWithRepositoryReturningNullFlow_returnSuccess() = runTest {
        val reminderTimeRepository: ReminderTimeRepository = mockk()

        coEvery { reminderTimeRepository.reminderTimeFlow() } returns flow {
            emit(null)
        }

        val useCase = ProdGetReminderTimeFlowUseCase(
            reminderTimeRepository = reminderTimeRepository,
        )

        val expectedResultFirstElement = null

        val result = useCase()
        val resultFlowFirstElement = (result as GetReminderTimeFlowResult.Success).reminderTimeFlow.first()

        Truth.assertThat(resultFlowFirstElement).isEqualTo(expectedResultFirstElement)
    }

    @Test
    fun useCase_invokedWithRepositoryReturningReminderTimePreferencesFlow_returnSuccess() = runTest {
        val hour = 8
        val minute = 30
        val reminderTimePreferences = ReminderTimePreferences(
            hour = hour,
            minute = minute,
        )

        val reminderTimeRepository: ReminderTimeRepository = mockk()

        coEvery { reminderTimeRepository.reminderTimeFlow() } returns flow {
            emit(reminderTimePreferences)
        }

        val useCase = ProdGetReminderTimeFlowUseCase(
            reminderTimeRepository = reminderTimeRepository,
        )

        val expectedReminderTime = ReminderTime(
            hour = hour,
            minute = minute,
        )

        val result = useCase()
        val resultFlowFirstElement = (result as GetReminderTimeFlowResult.Success).reminderTimeFlow.first()

        Truth.assertThat(resultFlowFirstElement).isEqualTo(expectedReminderTime)
    }
}
