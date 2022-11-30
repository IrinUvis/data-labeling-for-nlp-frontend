package it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto

data class RefreshTokenOutput(
    val userId: String,
    val idToken: String,
    val expiresIn: String,
    val refreshToken: String,
)
