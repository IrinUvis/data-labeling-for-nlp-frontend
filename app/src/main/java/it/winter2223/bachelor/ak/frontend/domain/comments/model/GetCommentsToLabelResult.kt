package it.winter2223.bachelor.ak.frontend.domain.comments.model

sealed class GetCommentsToLabelResult {
    data class Success(val comments: List<Comment>) : GetCommentsToLabelResult()

    sealed class Failure : GetCommentsToLabelResult() {
        object Network : Failure()

        object NoComments : Failure()

        object CommentsNumberOutOfRange : Failure()

        object ReadingToken : Failure()

        object NoToken : Failure()

        object Unknown : Failure()
    }
}
