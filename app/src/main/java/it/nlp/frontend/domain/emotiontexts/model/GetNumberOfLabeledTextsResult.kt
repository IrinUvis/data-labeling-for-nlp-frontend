package it.nlp.frontend.domain.emotiontexts.model

sealed class GetNumberOfLabeledTextsResult {

    data class Success(val count: Int): GetNumberOfLabeledTextsResult()

    sealed class Failure : GetNumberOfLabeledTextsResult() {
        object UnauthorizedUser : Failure()

        object Network : Failure()

        object ServiceUnavailable : Failure()

        object Unknown : Failure()

        object Unexpected : Failure()
    }
}
