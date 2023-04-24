package it.nlp.frontend.data.remote.model

@Suppress("MagicNumber")
enum class HttpStatus(val code: Int) {
    BadRequest(400),
    Unauthorized(401),
    ServiceUnavailable(503),
    GatewayTimeout(404),
}
