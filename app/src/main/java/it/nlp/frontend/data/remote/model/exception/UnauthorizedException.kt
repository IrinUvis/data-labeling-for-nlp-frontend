package it.nlp.frontend.data.remote.model.exception

class UnauthorizedException(
    override val message: String?,
    override val cause: Throwable?,
) : ApiException(message, cause)
