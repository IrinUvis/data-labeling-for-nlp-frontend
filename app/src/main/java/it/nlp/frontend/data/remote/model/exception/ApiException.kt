package it.nlp.frontend.data.remote.model.exception

open class ApiException(
    override val message: String?,
    override val cause: Throwable?,
) : Throwable(message, cause)
