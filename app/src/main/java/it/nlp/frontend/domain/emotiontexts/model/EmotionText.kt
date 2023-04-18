package it.nlp.frontend.domain.emotiontexts.model

data class EmotionText(
    val id: String,
    val text: String,
    val emotion: Emotion? = null,
)
