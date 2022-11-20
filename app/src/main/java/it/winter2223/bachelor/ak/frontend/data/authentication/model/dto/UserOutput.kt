package it.winter2223.bachelor.ak.frontend.data.authentication.model.dto

data class UserOutput(
    val email: String,
    val idToken: String,
    val refreshToken: String,
)
