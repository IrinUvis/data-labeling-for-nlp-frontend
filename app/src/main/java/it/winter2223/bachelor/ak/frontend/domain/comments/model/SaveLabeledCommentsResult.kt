package it.winter2223.bachelor.ak.frontend.domain.comments.model

sealed class SaveLabeledCommentsResult {
    object Success : SaveLabeledCommentsResult()

    sealed class Failure : SaveLabeledCommentsResult() {
        data class NonLabeledComments(val e: Exception) : Failure()

        object WrongEmotionParsing : Failure()

        object AlreadyAssignedByThisUser : Failure()

        object Network : Failure()

        object Unknown : Failure()

        object NoToken : Failure()

        object ReadingToken : Failure()

        object UnauthorizedUser : SaveLabeledCommentsResult()

        object ServiceUnavailable : SaveLabeledCommentsResult()
    }
}
