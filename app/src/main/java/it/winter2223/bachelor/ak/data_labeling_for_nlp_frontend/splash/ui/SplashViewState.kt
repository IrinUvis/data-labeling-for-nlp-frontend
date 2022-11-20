package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui

sealed class SplashViewState {
    object Loading : SplashViewState()

    data class Completed(val loggedIn: Boolean) : SplashViewState()
}
