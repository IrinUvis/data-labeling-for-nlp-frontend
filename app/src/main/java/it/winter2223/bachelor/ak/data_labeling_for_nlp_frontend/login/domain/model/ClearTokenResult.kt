package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model

sealed class ClearTokenResult {
    object Success : ClearTokenResult()

    object Failure : ClearTokenResult()
}
