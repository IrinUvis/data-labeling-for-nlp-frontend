package it.nlp.frontend.ui.home

sealed class HomeViewState {
    data class Loaded(
        val numberOfTextsToLabel: Int
    ) : HomeViewState()
}
