package it.winter2223.bachelor.ak.frontend.ui.splash

sealed class SplashViewState {
    object Loading : SplashViewState()

    data class Completed(val loggedIn: Boolean) : SplashViewState()
}
