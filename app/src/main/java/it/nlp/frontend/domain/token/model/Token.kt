package it.nlp.frontend.domain.token.model

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)
