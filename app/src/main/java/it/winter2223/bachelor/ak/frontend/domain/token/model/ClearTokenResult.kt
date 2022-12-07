package it.winter2223.bachelor.ak.frontend.domain.token.model

sealed class ClearTokenResult {
    object Success : ClearTokenResult()

    data class Failure(val e: Exception) : ClearTokenResult()
}
