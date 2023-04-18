package it.nlp.frontend.data.remote.model.exception.messages

// TODO: Modify messages, when they are modified in the backend
enum class TextExceptionMessage(val message: String) {
    NoCommentWithEnteredId("There is no comment with entered id: "),
    CommentsNumberOutOfRange("Comments number needs to be a number between 1 and 100"),
    CannotCompareNullComment("Null comment object cannot be compared"),
}
