package it.winter2223.bachelor.ak.frontend.data.authentication.model.dto

data class RefreshTokenOutput(
    val idToken: String,
    val refreshToken: String,
)
