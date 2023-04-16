package it.nlp.frontend.ui.splash

import it.nlp.frontend.ui.core.model.UiTheme

sealed class SplashViewState {
    object Loading : SplashViewState()

    data class Completed(
        val loggedIn: Boolean,
        val theme: UiTheme = UiTheme.System,
    ) : SplashViewState()
}
