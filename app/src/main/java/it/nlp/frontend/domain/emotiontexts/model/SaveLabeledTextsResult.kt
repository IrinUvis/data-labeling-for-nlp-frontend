package it.nlp.frontend.domain.emotiontexts.model

sealed class SaveLabeledTextsResult {
    object Success : SaveLabeledTextsResult()

    sealed class Failure : SaveLabeledTextsResult() {
        object UnauthorizedUser : Failure()

        object Network : Failure()

        object ServiceUnavailable : Failure()

        object Unknown : Failure()

        object Unexpected : Failure()
    }
}
