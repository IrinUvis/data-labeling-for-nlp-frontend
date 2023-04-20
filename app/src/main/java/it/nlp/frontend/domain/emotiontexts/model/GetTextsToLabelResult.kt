package it.nlp.frontend.domain.emotiontexts.model

sealed class GetTextsToLabelResult {
    data class Success(val emotionTexts: List<EmotionText>) : GetTextsToLabelResult()

    sealed class Failure : GetTextsToLabelResult() {
        object Network : Failure()

        object UnauthorizedUser : Failure()

        object NoTexts : Failure()

        object TextsNumberOutOfRange : Failure()

        object Unknown : Failure()

        object ServiceUnavailable : GetTextsToLabelResult()
    }
}
