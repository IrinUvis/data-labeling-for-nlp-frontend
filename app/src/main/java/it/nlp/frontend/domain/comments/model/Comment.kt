package it.nlp.frontend.domain.comments.model

data class Comment(
    val id: String,
    val text: String,
    val emotion: Emotion? = null,
)
