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
import javax.inject.Inject

class ProdGetTextsToLabelUseCase @Inject constructor(
    private val emotionTextRepository: EmotionTextRepository,
) : GetTextsToLabelUseCase {
    override suspend fun invoke(quantity: Int): GetTextsToLabelResult {
        val textsResult = emotionTextRepository.fetchEmotionTextsToBeAssigned(
            emotionTextsNumber = quantity,
        )

        return textsResult.fold(
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
