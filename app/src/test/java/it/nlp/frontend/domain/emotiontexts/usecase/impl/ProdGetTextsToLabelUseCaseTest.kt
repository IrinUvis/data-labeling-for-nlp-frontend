@file:Suppress("MaxLineLength")

package it.nlp.frontend.domain.emotiontexts.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.emotion.texts.model.dto.EmotionTextOutput
import it.nlp.frontend.data.remote.emotion.texts.model.exception.EmotionTextException
import it.nlp.frontend.data.remote.emotion.texts.repository.EmotionTextRepository
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.data.remote.model.exception.UnauthorizedException
import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.GetTextsToLabelResult
import it.nlp.frontend.domain.token.model.GetTokenFlowResult
import it.nlp.frontend.domain.token.model.Token
import it.nlp.frontend.domain.token.usecase.GetTokenFlowUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdGetTextsToLabelUseCaseTest {

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningSuccess_returnsGetTextsToLabelSuccess() =
        runTest {
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val textsNumber = 10
            val textId = "textId"
            val textContent = "content"

            val emotionTextOutput = EmotionTextOutput(
                id = textId,
                content = textContent
            )
            val emotionText = EmotionText(
                id = textId,
                text = textContent,
            )
            val emotionTextOutputList = List(textsNumber) { emotionTextOutput }
            val textsList = List(textsNumber) { emotionText }

            val retrievedToken = Token(
                accessToken = accessToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    userId = userId,
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Success(emotionTextOutputList)

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Success(textsList)

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningFailure_returnsReadingTokenGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Failure(IOException())

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.ReadingToken

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningNull_returnNoTokenGetTextsToLabelResult() = runTest {
        val textsNumber = 10

        val emotionTextRepositoryMock: EmotionTextRepository = mockk()
        val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

        coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
            tokenFlow = flow { emit(null) }
        )

        val useCase = ProdGetTextsToLabelUseCase(
            emotionTextRepository = emotionTextRepositoryMock,
            getTokenFlowUseCase = getTokenFlowUseCaseMock
        )

        val result = useCase(textsNumber)

        val expectedResult = GetTextsToLabelResult.Failure.NoToken

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningEmptyList_returnNoTextsGetTextsToLabelResult() =
        runTest {
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val textsNumber = 10

            val textsOutputList = emptyList<EmotionTextOutput>()

            val retrievedToken = Token(
                accessToken = accessToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    userId = userId,
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Success(textsOutputList)

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.NoTexts

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningUnauthorizedException_returnUnauthorizedUserGetTextsToLabelResult() =
        runTest {
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val textsNumber = 10

            val retrievedToken = Token(
                accessToken = accessToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    userId = userId,
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Failure(UnauthorizedException(null, null))

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.UnauthorizedUser

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningNetworkException_returnNetworkGetTextsToLabelResult() =
        runTest {
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val textsNumber = 10

            val retrievedToken = Token(
                accessToken = accessToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    userId = userId,
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Failure(NetworkException(null, null))

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.Network

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningServiceUnavailableException_returnServiceUnavailableGetTextsToLabelResult() =
        runTest {
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val textsNumber = 10

            val retrievedToken = Token(
                accessToken = accessToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    userId = userId,
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Failure(ServiceUnavailableException(null, null))

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.ServiceUnavailable

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningTextsNumberOutOfRangeException_returnTextsNumberOutOfRangeGetTextsToLabelResult() =
        runTest {
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val textsNumber = 10
            val errorMessage = "errorMessage"

            val retrievedToken = Token(
                accessToken = accessToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    userId = userId,
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Failure(EmotionTextException.NumberOutOfRange(errorMessage))

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.TextsNumberOutOfRange

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningCannotCompareNullsException_returnUnknownGetTextsToLabelResult() =
        runTest {
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val textsNumber = 10
            val errorMessage = "errorMessage"

            val retrievedToken = Token(
                accessToken = accessToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    userId = userId,
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Failure(EmotionTextException.CannotCompareNulls(errorMessage))

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningUnknownException_returnUnknownGetTextsToLabelResult() =
        runTest {
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val textsNumber = 10
            val errorMessage = "errorMessage"

            val retrievedToken = Token(
                accessToken = accessToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    userId = userId,
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Failure(EmotionTextException.Unknown(errorMessage))

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }
}
