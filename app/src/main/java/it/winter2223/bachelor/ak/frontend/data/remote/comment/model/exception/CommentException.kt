package it.winter2223.bachelor.ak.frontend.data.remote.comment.model.exception

import io.ktor.client.plugins.ResponseException

sealed class CommentException(override val message: String?) : Throwable(message) {
    data class NoCommentWithEnteredId(override val message: String) : CommentException(message)

    data class CommentsNumberIsNotInteger(override val message: String) : CommentException(message)

    data class CommentsNumberOutOfRange(override val message: String) : CommentException(message)

    data class CannotCompareNullComment(override val message: String) : CommentException(message)

    data class VideosFetchingError(override val message: String) : CommentException(message)

    data class CommentsFetchingError(override val message: String) : CommentException(message)

    data class Unknown(override val message: String?) : CommentException(message)
}

fun ResponseException.toCommentException(): CommentException {
    return when (val message = this.message) {
        "There is no comment with entered id" -> CommentException.NoCommentWithEnteredId(message)
        "Comments number is not an integer value" ->
            CommentException.CommentsNumberIsNotInteger(message)
        "Comments number needs to be a number between 1 and 100" ->
            CommentException.CommentsNumberOutOfRange(message)
        "Null comment object cannot be compare" -> CommentException.CannotCompareNullComment(message)
        "Error while fetching You Tube videos list" -> CommentException.VideosFetchingError(message)
        "Error while fetching You Tube comments list" -> CommentException.CommentsFetchingError(message)
        else -> CommentException.Unknown(message)
    }
}
