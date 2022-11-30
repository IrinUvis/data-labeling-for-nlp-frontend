package it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.exception

import io.ktor.client.plugins.ResponseException

sealed class AuthenticationException : Throwable() {
    object InvalidEmailAddress : AuthenticationException()

    object InvalidPassword : AuthenticationException()

    object SigningUpFailed : AuthenticationException()

    object SigningInFailed : AuthenticationException()

    object TokenRefreshingFailed : AuthenticationException()

    object SettingUserClaimsFailed : AuthenticationException()

    object Unknown : AuthenticationException()
}

fun ResponseException.toAuthenticationException(): AuthenticationException {
    return when (this.message) {
        "Email address is invalid" -> AuthenticationException.InvalidEmailAddress
        "Password must be at least 6 characters" -> AuthenticationException.InvalidPassword
        "Failed to sign up" -> AuthenticationException.SigningUpFailed
        "Failed to sign in" -> AuthenticationException.SigningInFailed
        "Failed to refresh token" -> AuthenticationException.TokenRefreshingFailed
        "Failed to set user claims" -> AuthenticationException.SettingUserClaimsFailed
        else -> AuthenticationException.Unknown
    }
}
