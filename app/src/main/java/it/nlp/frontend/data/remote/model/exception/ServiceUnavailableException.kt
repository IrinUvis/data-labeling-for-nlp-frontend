package it.nlp.frontend.data.remote.model.exception

class ServiceUnavailableException(
    override val message: String?,
    override val cause: Throwable?,
) : ApiException(message, cause)
