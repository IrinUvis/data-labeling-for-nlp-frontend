package it.winter2223.bachelor.ak.frontend.ui.splash

import it.winter2223.bachelor.ak.frontend.ui.core.model.UiTheme

sealed class SplashViewState {
    object Loading : SplashViewState()

    data class Completed(
        val loggedIn: Boolean,
        val theme: UiTheme = UiTheme.System,
    ) : SplashViewState()
}
