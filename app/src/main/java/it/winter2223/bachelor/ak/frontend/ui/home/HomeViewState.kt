package it.winter2223.bachelor.ak.frontend.ui.home

sealed class HomeViewState {
    data class Loaded(
        val numberOfCommentsToLabel: Int
    ) : HomeViewState()
}
