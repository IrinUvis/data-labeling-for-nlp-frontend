package it.nlp.frontend.data.remote.authentication.model.dto

data class RefreshTokenOutput(
    val userId: String,
    val accessTokenOutput: TokenOutput,
    val refreshTokenOutput: TokenOutput,
)
