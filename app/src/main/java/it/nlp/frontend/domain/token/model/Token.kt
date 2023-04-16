package it.nlp.frontend.domain.token.model

data class Token(
    val authToken: String,
    val refreshToken: String,
    val userId: String,
)
