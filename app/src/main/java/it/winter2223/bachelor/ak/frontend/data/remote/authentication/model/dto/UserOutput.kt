package it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto

data class UserOutput(
    val email: String,
    val userId: String,
    val idToken: String,
    val expiresIn: String,
    val refreshToken: String,
)
