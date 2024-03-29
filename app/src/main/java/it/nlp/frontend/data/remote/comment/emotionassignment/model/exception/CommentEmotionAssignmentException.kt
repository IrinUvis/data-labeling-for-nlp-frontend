package it.nlp.frontend.data.remote.comment.emotionassignment.model.exception

import io.ktor.client.plugins.ResponseException
import it.nlp.frontend.data.remote.model.exception.ApiException

sealed class CommentEmotionAssignmentException(override val message: String?) : ApiException(message, null) {
    data class AssignmentAlreadyExists(override val message: String) : CommentEmotionAssignmentException(message)

    data class WrongEmotion(override val message: String) : CommentEmotionAssignmentException(message)

    data class Unknown(override val message: String?) : CommentEmotionAssignmentException(message)
}

fun ResponseException.toCommentEmotionAssignmentException(): CommentEmotionAssignmentException {
    return when (val message = this.message) {
        "There already exists this user's assignment for this content" ->
            CommentEmotionAssignmentException.AssignmentAlreadyExists(message)
        "Entered emotion does not exits" -> CommentEmotionAssignmentException.WrongEmotion(message)
        else -> CommentEmotionAssignmentException.Unknown(message)
    }
}
