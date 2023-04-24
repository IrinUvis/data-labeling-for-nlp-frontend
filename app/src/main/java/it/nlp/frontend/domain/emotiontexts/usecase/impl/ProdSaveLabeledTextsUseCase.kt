package it.nlp.frontend.domain.emotiontexts.usecase.impl

import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentInput
import it.nlp.frontend.data.remote.emotion.assignments.repository.TextEmotionAssignmentRepository
import it.nlp.frontend.data.remote.model.ApiResponse
import it.nlp.frontend.data.remote.model.HttpStatus
import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.SaveLabeledTextsResult
import it.nlp.frontend.domain.emotiontexts.usecase.SaveLabeledTextsUseCase
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ProdSaveLabeledTextsUseCase @Inject constructor(
    private val textEmotionAssignmentRepository: TextEmotionAssignmentRepository,
) : SaveLabeledTextsUseCase {
    @Suppress("SwallowedException")
    override suspend fun invoke(emotionTexts: List<EmotionText>): SaveLabeledTextsResult {
        return try {
            emotionTexts.forEach { emotionText -> requireNotNull(emotionText.emotion) }

            val inputs = emotionTexts.map {
                TextEmotionAssignmentInput(
                    textId = it.id,
                    emotion = it.emotion!!.toUppercaseString(),
                )
            }

            when (val apiResponse = textEmotionAssignmentRepository.postAssignments(inputs)) {
                is ApiResponse.Success -> SaveLabeledTextsResult.Success
                is ApiResponse.Failure -> saveLabeledTextsResultForApiResponseFailure(apiResponse)
                is ApiResponse.Exception -> saveLabeledTextsResultForApiResponseException(apiResponse)
            }
        } catch (e: IllegalArgumentException) {
            SaveLabeledTextsResult.Failure.Unexpected
        }
    }

    private fun saveLabeledTextsResultForApiResponseFailure(
        apiFailureResponse: ApiResponse.Failure
    ): SaveLabeledTextsResult.Failure {
        return when (apiFailureResponse.code) {
            HttpStatus.ServiceUnavailable.code, HttpStatus.GatewayTimeout.code
            -> SaveLabeledTextsResult.Failure.ServiceUnavailable

            HttpStatus.Unauthorized.code -> SaveLabeledTextsResult.Failure.UnauthorizedUser
            HttpStatus.BadRequest.code -> SaveLabeledTextsResult.Failure.Unexpected
            else -> SaveLabeledTextsResult.Failure.Unknown
        }
    }

    private fun saveLabeledTextsResultForApiResponseException(
        apiResponseException: ApiResponse.Exception
    ): SaveLabeledTextsResult.Failure {
        return when (apiResponseException.exception) {
            is ConnectException, is SocketTimeoutException -> SaveLabeledTextsResult.Failure.Network
            else -> SaveLabeledTextsResult.Failure.Unexpected
        }
    }
}
