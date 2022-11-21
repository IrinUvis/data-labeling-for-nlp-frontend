package it.winter2223.bachelor.ak.frontend.domain.comments.model

sealed class SaveLabeledCommentsResult {
    object Success : SaveLabeledCommentsResult()

    sealed class Failure : SaveLabeledCommentsResult() {
        object NonLabeledComments : Failure()

        object Unknown : Failure()
    }
}
