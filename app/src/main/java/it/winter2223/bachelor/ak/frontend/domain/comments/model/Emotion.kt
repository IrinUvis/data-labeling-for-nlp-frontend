package it.winter2223.bachelor.ak.frontend.domain.comments.model

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
