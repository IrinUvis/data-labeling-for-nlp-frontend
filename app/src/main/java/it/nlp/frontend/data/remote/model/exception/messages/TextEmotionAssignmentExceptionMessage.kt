package it.nlp.frontend.data.remote.model.exception.messages

enum class TextEmotionAssignmentExceptionMessage(val message: String) {
    AssignmentAlreadyExists("There already exists this user's assignment for the text with ID: "),
    WrongEmotion("Entered emotion does not exist: "),
    FailedToWriteCsv("Failed to write to csv file"),
}
