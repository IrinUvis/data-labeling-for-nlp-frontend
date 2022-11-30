package it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.exception

import io.ktor.client.plugins.ResponseException

sealed class CommentEmotionAssignmentException(override val message: String?) : Throwable(message) {
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
