package it.nlp.frontend.data.remote.model.exception.messages

enum class TextExceptionMessage(val message: String) {
    NoTextWithEnteredId("There is no text with entered ID: "),
    TextsNumberOutOfRange("Texts number needs to be a number between 1 and 100"),
    CannotCompareNullText("Null text objects cannot be compared"),
}
