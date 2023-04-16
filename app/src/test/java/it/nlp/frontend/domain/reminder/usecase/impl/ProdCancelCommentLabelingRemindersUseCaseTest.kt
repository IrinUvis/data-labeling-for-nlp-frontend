package it.nlp.frontend.domain.reminder.usecase.impl

import androidx.work.Operation
import androidx.work.WorkManager
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.CancelCommentLabelingRemindersResult
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl.ProdCancelCommentLabelingRemindersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ProdCancelCommentLabelingRemindersUseCaseTest {

    @Test
    fun useCase_invoked_cancelCalledOnceAndReturnedSuccess() = runTest {
        val workManagerMockk: WorkManager = mockk()
        val operationMock: Operation = mockk()

        coEvery { workManagerMockk.cancelUniqueWork(any()) } returns operationMock

        val useCase = ProdCancelCommentLabelingRemindersUseCase(
            workManager = workManagerMockk,
        )

        val result = useCase()

        coVerify(exactly = 1) {
            workManagerMockk.cancelUniqueWork(any<String>())
        }
        Truth.assertThat(result).isEqualTo(CancelCommentLabelingRemindersResult.Success)
    }
}
