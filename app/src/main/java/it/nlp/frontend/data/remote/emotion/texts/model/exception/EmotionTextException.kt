package it.nlp.frontend.data.remote.emotion.texts.model.exception

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.remote.model.exception.messages.TextExceptionMessage

sealed class EmotionTextException(override val message: String?) : ApiException(message, null) {

    data class NumberOutOfRange(override val message: String) : EmotionTextException(message)

    data class CannotCompareNulls(override val message: String) : EmotionTextException(message)

    data class Unknown(override val message: String?) : EmotionTextException(message)
}

suspend fun ResponseException.toEmotionTextException(): EmotionTextException {
    val message = this.response.bodyAsText()

    return when {
        message.contains(TextExceptionMessage.TextsNumberOutOfRange.message) ->
            EmotionTextException.NumberOutOfRange(
                message
            )

        message.contains(TextExceptionMessage.CannotCompareNullText.message) ->
            EmotionTextException.CannotCompareNulls(
                message
            )

        else -> EmotionTextException.Unknown(message)
    }
}
