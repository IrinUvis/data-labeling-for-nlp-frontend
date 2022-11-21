package it.winter2223.bachelor.ak.frontend.domain.token.model

sealed class ClearTokenResult {
    object Success : ClearTokenResult()

    object Failure : ClearTokenResult()
}
