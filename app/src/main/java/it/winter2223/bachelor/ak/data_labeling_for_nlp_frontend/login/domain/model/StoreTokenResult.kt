package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model

sealed class StoreTokenResult {
    object Success : StoreTokenResult()

    object Failure : StoreTokenResult()
}
