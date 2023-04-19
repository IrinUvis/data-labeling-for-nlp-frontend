package it.nlp.frontend.data.remote.model.exception.messages

enum class ChannelExceptionMessage(val message: String) {
    ContainsNullChannelId("Entered list contains null ID"),
    NullChannelId("Entered channel ID is null"),
    NoChannelWithEnteredId("There is no channel with entered ID: "),
}
