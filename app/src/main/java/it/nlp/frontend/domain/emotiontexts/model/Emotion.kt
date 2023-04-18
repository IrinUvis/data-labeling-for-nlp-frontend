package it.nlp.frontend.domain.emotiontexts.model

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
