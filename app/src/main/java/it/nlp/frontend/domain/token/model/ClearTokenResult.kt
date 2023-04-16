package it.nlp.frontend.domain.token.model

sealed class ClearTokenResult {
    object Success : ClearTokenResult()

    data class Failure(val e: Exception) : ClearTokenResult()
}
