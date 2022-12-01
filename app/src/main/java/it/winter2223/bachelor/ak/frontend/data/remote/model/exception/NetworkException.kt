package it.winter2223.bachelor.ak.frontend.data.remote.model.exception

class NetworkException(override val message: String?, override val cause: Throwable?) :
    ApiException(message, cause)
