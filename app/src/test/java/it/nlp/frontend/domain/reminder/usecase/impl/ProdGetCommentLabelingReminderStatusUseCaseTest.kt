package it.nlp.frontend.domain.reminder.usecase.impl

import androidx.work.Data
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.common.truth.Truth
import com.google.common.util.concurrent.ListenableFuture
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.domain.reminder.model.GetCommentLabelingReminderStatusResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class ProdGetCommentLabelingReminderStatusUseCaseTest {

    @Test
    fun useCase_invoked_returnsSuccessWithIsScheduledFalse() = runTest {
        val workManagerMock: WorkManager = mockk()
        val listenableFutureMock: ListenableFuture<List<WorkInfo>> = mockk()
        val workInfos: List<WorkInfo> = emptyList()

        coEvery { workManagerMock.getWorkInfosForUniqueWork(any()) } returns listenableFutureMock
        coEvery { listenableFutureMock.get() } returns workInfos

        val useCase = ProdGetCommentLabelingReminderStatusUseCase(
            workManager = workManagerMock,
        )

        val expectedResult = GetCommentLabelingReminderStatusResult.Success(isScheduled = false)

        val result = useCase()

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun useCase_invoked_returnsSuccessWithIsScheduledTrue() = runTest {
        val workManagerMock: WorkManager = mockk()
        val listenableFutureMock: ListenableFuture<List<WorkInfo>> = mockk()
        val dataMock: Data = mockk()
        val workInfos: List<WorkInfo> = listOf(
            WorkInfo(
                UUID.randomUUID(),
                WorkInfo.State.RUNNING,
                dataMock,
                emptyList(),
                dataMock,
                0
            )
        )

        coEvery { workManagerMock.getWorkInfosForUniqueWork(any()) } returns listenableFutureMock
        coEvery { listenableFutureMock.get() } returns workInfos

        val useCase = ProdGetCommentLabelingReminderStatusUseCase(
            workManager = workManagerMock,
        )

        val expectedResult = GetCommentLabelingReminderStatusResult.Success(isScheduled = true)

        val result = useCase()

        Truth.assertThat(result).isEqualTo(expectedResult)
    }
}
