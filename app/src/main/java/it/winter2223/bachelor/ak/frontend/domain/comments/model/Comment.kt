package it.winter2223.bachelor.ak.frontend.domain.comments.model

data class Comment(
    val id: String,
    val text: String,
    val emotion: Emotion? = null,
)
