package it.nlp.frontend.domain.emotiontexts.usecase.impl

import it.nlp.frontend.data.remote.emotion.texts.repository.impl.NetworkEmotionTextRepository
import it.nlp.frontend.data.remote.model.ApiResponse
import it.nlp.frontend.data.remote.model.HttpStatus
import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.GetTextsToLabelResult
import it.nlp.frontend.domain.emotiontexts.usecase.GetTextsToLabelUseCase
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ProdGetTextsToLabelUseCase @Inject constructor(
    private val emotionTextRepository: NetworkEmotionTextRepository,
) : GetTextsToLabelUseCase {
    override suspend fun invoke(quantity: Int): GetTextsToLabelResult {
        return when (val apiResponse = emotionTextRepository.getTextsForAssignment(quantity)) {
            is ApiResponse.Success -> {
                val texts = apiResponse.data
                if (texts.isEmpty()) {
                    GetTextsToLabelResult.Failure.NoTexts
                } else {
                    GetTextsToLabelResult.Success(
                        texts.map { text -> EmotionText(text.emotionTextId, text.content) }
                    )
                }
            }

            is ApiResponse.Failure -> getTextsToLabelResultForApiResponseFailure(apiResponse)
            is ApiResponse.Exception -> getTextsToLabelResultForApiResponseException(apiResponse)
        }
    }

    private fun getTextsToLabelResultForApiResponseFailure(
        apiResponseFailure: ApiResponse.Failure
    ): GetTextsToLabelResult.Failure {
        return when (apiResponseFailure.code) {
            HttpStatus.ServiceUnavailable.code, HttpStatus.GatewayTimeout.code
            -> GetTextsToLabelResult.Failure.ServiceUnavailable

            HttpStatus.Unauthorized.code -> GetTextsToLabelResult.Failure.UnauthorizedUser
            HttpStatus.BadRequest.code -> GetTextsToLabelResult.Failure.Unexpected
            else -> GetTextsToLabelResult.Failure.Unknown
        }
    }

    private fun getTextsToLabelResultForApiResponseException(
        apiResponseException: ApiResponse.Exception
    ): GetTextsToLabelResult.Failure {
        return when (apiResponseException.exception) {
            is ConnectException, is SocketTimeoutException -> GetTextsToLabelResult.Failure.Network
            else -> GetTextsToLabelResult.Failure.Unexpected
        }
    }
}
