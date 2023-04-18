package it.nlp.frontend.domain.emotiontexts.usecase.impl

import it.nlp.frontend.data.remote.emotion.texts.model.exception.EmotionTextException
import it.nlp.frontend.data.remote.emotion.texts.repository.EmotionTextRepository
import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.data.remote.model.exception.UnauthorizedException
import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.GetTextsToLabelResult
import it.nlp.frontend.domain.emotiontexts.usecase.GetTextsToLabelUseCase
import it.nlp.frontend.domain.token.model.GetTokenFlowResult
import it.nlp.frontend.domain.token.usecase.GetTokenFlowUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProdGetTextsToLabelUseCase @Inject constructor(
    private val emotionTextRepository: EmotionTextRepository,
    private val getTokenFlowUseCase: GetTokenFlowUseCase,
) : GetTextsToLabelUseCase {
    override suspend fun invoke(quantity: Int): GetTextsToLabelResult {
        return when (val tokenFlowResult = getTokenFlowUseCase()) {
            is GetTokenFlowResult.Success -> {
                val token = tokenFlowResult.tokenFlow.first()
                token?.userId?.let { userId ->
                    val textsResult = emotionTextRepository.fetchEmotionTextsToBeAssigned(
                        userId = userId,
                        emotionTextsNumber = quantity,
                    )

                    textsResult.fold(
                        onSuccess = { textDtos ->
                            if (textDtos.isNotEmpty()) {
                                GetTextsToLabelResult.Success(
                                    emotionTexts = textDtos.map { dto ->
                                        EmotionText(
                                            id = dto.id,
                                            text = dto.content,
                                        )
                                    }
                                )
                            } else {
                                GetTextsToLabelResult.Failure.NoTexts
                            }
                        },
                        onFailure = { apiException ->
                            getTextsToLabelResultForApiException(apiException)
                        }
                    )
                } ?: GetTextsToLabelResult.Failure.NoToken
            }

            is GetTokenFlowResult.Failure -> {
                GetTextsToLabelResult.Failure.ReadingToken
            }
        }
    }

    private fun getTextsToLabelResultForApiException(apiException: ApiException): GetTextsToLabelResult {
        return when (apiException) {
            is ServiceUnavailableException -> GetTextsToLabelResult.Failure.ServiceUnavailable
            is NetworkException -> GetTextsToLabelResult.Failure.Network
            is UnauthorizedException -> GetTextsToLabelResult.Failure.UnauthorizedUser
            is EmotionTextException -> {
                when (apiException) {
                    is EmotionTextException.NumberOutOfRange -> GetTextsToLabelResult.Failure.TextsNumberOutOfRange
                    else -> GetTextsToLabelResult.Failure.Unknown
                }
            }

            else -> GetTextsToLabelResult.Failure.Unknown
        }
    }
}
