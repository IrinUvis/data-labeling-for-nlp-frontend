package it.nlp.frontend.domain.emotiontexts.model

sealed class SaveLabeledTextsResult {
    object Success : SaveLabeledTextsResult()

    sealed class Failure : SaveLabeledTextsResult() {
        data class NonLabeledTexts(val e: Exception) : Failure()

        object WrongEmotionParsing : Failure()

        object AlreadyAssignedByThisUser : Failure()

        object Network : Failure()

        object Unknown : Failure()

        object NoToken : Failure()

        object ReadingToken : Failure()

        object UnauthorizedUser : SaveLabeledTextsResult()

        object ServiceUnavailable : SaveLabeledTextsResult()
    }
}
