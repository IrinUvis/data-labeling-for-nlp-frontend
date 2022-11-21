package it.winter2223.bachelor.ak.frontend.domain.token.model

data class Token(
    val authToken: String,
    val refreshToken: String,
    val userId: String,
)
