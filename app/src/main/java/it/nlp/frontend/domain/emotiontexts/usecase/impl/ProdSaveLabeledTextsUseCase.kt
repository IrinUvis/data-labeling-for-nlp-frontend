package it.nlp.frontend.domain.emotiontexts.usecase.impl

import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentInput
import it.nlp.frontend.data.remote.emotion.assignments.model.exception.TextEmotionAssignmentException
import it.nlp.frontend.data.remote.emotion.assignments.repository.TextEmotionAssignmentRepository
import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.data.remote.model.exception.UnauthorizedException
import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.SaveLabeledTextsResult
import it.nlp.frontend.domain.emotiontexts.usecase.SaveLabeledTextsUseCase
import it.nlp.frontend.domain.token.model.GetTokenFlowResult
import it.nlp.frontend.domain.token.usecase.GetTokenFlowUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProdSaveLabeledTextsUseCase @Inject constructor(
    private val textEmotionAssignmentRepository: TextEmotionAssignmentRepository,
    private val getTokenFlowUseCase: GetTokenFlowUseCase,
) : SaveLabeledTextsUseCase {
    override suspend fun invoke(emotionTexts: List<EmotionText>): SaveLabeledTextsResult {
        return try {
            emotionTexts.forEach { emotionText -> requireNotNull(emotionText.emotion) }
            when (val tokenFlowResult = getTokenFlowUseCase()) {
                is GetTokenFlowResult.Success -> {
                    val token = tokenFlowResult.tokenFlow.first()
                    token?.userId?.let { userId ->
                        val inputs = emotionTexts.map {
                            TextEmotionAssignmentInput(
                                userId = userId,
                                textId = it.id,
                                emotion = it.emotion!!.toUppercaseString(),
                            )
                        }

                        val postedAssignmentsResult =
                            textEmotionAssignmentRepository.postTextEmotionAssignments(inputs)

                        postedAssignmentsResult.fold(
                            onSuccess = {
                                SaveLabeledTextsResult.Success
                            },
                            onFailure = { apiException ->
                                saveLabeledTextsResultForApiException(apiException)
                            },
                        )

                    } ?: SaveLabeledTextsResult.Failure.NoToken
                }

                is GetTokenFlowResult.Failure -> {
                    SaveLabeledTextsResult.Failure.ReadingToken
                }
            }
        } catch (e: IllegalArgumentException) {
            SaveLabeledTextsResult.Failure.NonLabeledTexts(e)
        }
    }

    private fun saveLabeledTextsResultForApiException(
        apiException: ApiException
    ): SaveLabeledTextsResult = when (apiException) {
        is ServiceUnavailableException -> SaveLabeledTextsResult.Failure.ServiceUnavailable
        is NetworkException -> SaveLabeledTextsResult.Failure.Network
        is UnauthorizedException -> SaveLabeledTextsResult.Failure.UnauthorizedUser
        is TextEmotionAssignmentException -> {
            when (apiException) {
                is TextEmotionAssignmentException.WrongEmotion -> SaveLabeledTextsResult.Failure.WrongEmotionParsing
                is TextEmotionAssignmentException.AssignmentAlreadyExists ->
                    SaveLabeledTextsResult.Failure.AlreadyAssignedByThisUser

                else -> SaveLabeledTextsResult.Failure.Unknown
            }
        }

        else -> SaveLabeledTextsResult.Failure.Unknown
    }
}
