package it.nlp.frontend.domain.comments.model

enum class Emotion {
    Anger,
    Fear,
    Joy,
    Love,
    Sadness,
    Surprise,
    Unspecifiable;

    fun toUppercaseString() = this.name.uppercase()
}
