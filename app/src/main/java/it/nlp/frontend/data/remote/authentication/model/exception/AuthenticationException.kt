package it.nlp.frontend.data.remote.authentication.model.exception

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import it.nlp.frontend.data.remote.model.exception.ApiException

sealed class AuthenticationException(override val message: String?) : ApiException(message, null) {
    data class InvalidEmailAddress(override val message: String) : AuthenticationException(message)

    data class InvalidPassword(override val message: String) : AuthenticationException(message)

    data class SigningUpFailed(override val message: String) : AuthenticationException(message)

    data class SigningInFailed(override val message: String) : AuthenticationException(message)

    data class TokenRefreshingFailed(override val message: String) : AuthenticationException(message)

    data class SettingUserClaimsFailed(override val message: String) : AuthenticationException(message)

    data class Unknown(override val message: String?) : AuthenticationException(message)
}

suspend fun ResponseException.toAuthenticationException(): AuthenticationException {
    return when (val message = this.response.bodyAsText()) {
        "Email address is invalid" -> AuthenticationException.InvalidEmailAddress(message)
        "Password must be at least 6 characters" -> AuthenticationException.InvalidPassword(message)
        "Failed to sign up" -> AuthenticationException.SigningUpFailed(message)
        "Failed to sign in" -> AuthenticationException.SigningInFailed(message)
        "Failed to refresh token" -> AuthenticationException.TokenRefreshingFailed(message)
        "Failed to set user claims" -> AuthenticationException.SettingUserClaimsFailed(message)
        else -> AuthenticationException.Unknown(message)
    }
}
