package it.nlp.frontend.domain.emotiontexts.model

sealed class GetTextsToLabelResult {
    data class Success(val emotionTexts: List<EmotionText>) : GetTextsToLabelResult()

    sealed class Failure : GetTextsToLabelResult() {
        object NoTexts : Failure()

        object UnauthorizedUser : Failure()

        object Network : Failure()

        object ServiceUnavailable : Failure()

        object Unknown : Failure()

        object Unexpected : Failure()
    }
}
