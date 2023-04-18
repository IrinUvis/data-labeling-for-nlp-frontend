package it.nlp.frontend.data.remote.authentication.model.exception

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.remote.model.exception.messages.SecurityExceptionMessage

sealed class AuthenticationException(override val message: String?) : ApiException(message, null) {
    data class InvalidEmailAddress(override val message: String) : AuthenticationException(message)

    data class InvalidPassword(override val message: String) : AuthenticationException(message)

    data class EmailAlreadyTaken(override val message: String) : AuthenticationException(message)

    data class BadCredentials(override val message: String) : AuthenticationException(message)

    data class Unknown(override val message: String?) : AuthenticationException(message)
}

suspend fun ResponseException.toAuthenticationException(): AuthenticationException {
    val message = this.response.bodyAsText()
    return when {
        message.contains(SecurityExceptionMessage.InvalidEmailAddress.message) ->
            AuthenticationException.InvalidEmailAddress(message)

        message.contains(SecurityExceptionMessage.InvalidPassword.message) ->
            AuthenticationException.InvalidPassword(message)

        message.contains(SecurityExceptionMessage.EmailAlreadyTaken.message) ->
            AuthenticationException.EmailAlreadyTaken(message)

        message.contains(SecurityExceptionMessage.BadCredentials.message) ->
            AuthenticationException.BadCredentials(message)

        else -> AuthenticationException.Unknown(message)
    }
}
