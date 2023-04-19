package it.nlp.frontend.data.remote.model.exception.messages

enum class TextExceptionMessage(val message: String) {
    NoCommentWithEnteredId("There is no text with entered ID: "),
    CommentsNumberOutOfRange("Texts number needs to be a number between 1 and 100"),
    CannotCompareNullComment("Null text objects cannot be compared"),
}
