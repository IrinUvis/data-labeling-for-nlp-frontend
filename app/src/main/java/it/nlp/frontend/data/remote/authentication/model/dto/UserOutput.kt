package it.nlp.frontend.data.remote.authentication.model.dto

data class UserOutput(
    val userId: String,
    val email: String,
    val userRoleOutput: UserRoleOutput,
    val accessTokenOutput: TokenOutput,
    val refreshTokenOutput: TokenOutput,
)
