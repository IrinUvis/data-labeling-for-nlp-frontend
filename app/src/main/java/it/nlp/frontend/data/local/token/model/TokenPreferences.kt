package it.nlp.frontend.data.local.token.model

data class TokenPreferences(
    val authToken: String,
    val refreshToken: String,
    val userId: String,
)
