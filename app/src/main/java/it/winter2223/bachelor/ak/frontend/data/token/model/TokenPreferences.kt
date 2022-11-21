package it.winter2223.bachelor.ak.frontend.data.token.model

data class TokenPreferences(
    val authToken: String,
    val refreshToken: String,
    val userId: String,
)
