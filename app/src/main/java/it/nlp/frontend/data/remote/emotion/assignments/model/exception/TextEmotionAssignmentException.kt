package it.nlp.frontend.data.remote.emotion.assignments.model.exception

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.remote.model.exception.messages.TextEmotionAssignmentExceptionMessage

sealed class TextEmotionAssignmentException(override val message: String?) :
    ApiException(message, null) {
    data class AssignmentAlreadyExists(override val message: String) :
        TextEmotionAssignmentException(message)

    data class WrongEmotion(override val message: String) : TextEmotionAssignmentException(message)

    data class Unknown(override val message: String?) : TextEmotionAssignmentException(message)
}

suspend fun ResponseException.toTextEmotionAssignmentException(): TextEmotionAssignmentException {
    val message = this.response.bodyAsText()
    return when {
        message.contains(TextEmotionAssignmentExceptionMessage.AssignmentAlreadyExists.message) ->
            TextEmotionAssignmentException.AssignmentAlreadyExists(message)

        message.contains(TextEmotionAssignmentExceptionMessage.WrongEmotion.message) ->
            TextEmotionAssignmentException.WrongEmotion(message)

        else -> TextEmotionAssignmentException.Unknown(message)
    }
}
