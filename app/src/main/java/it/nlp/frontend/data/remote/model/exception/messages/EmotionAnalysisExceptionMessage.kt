package it.nlp.frontend.data.remote.model.exception.messages

enum class EmotionAnalysisExceptionMessage(val message: String) {
    FailedToLoadNlpModel("Failed to load model responsible for inferring emotions from texts"),
    FailedToInferEmotion("Failed to infer an emotion from a given text")
}
