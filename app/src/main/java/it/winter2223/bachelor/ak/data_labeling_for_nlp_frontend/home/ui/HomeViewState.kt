package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui

sealed class HomeViewState {
    object Loading : HomeViewState()

    data class Loaded(
        val numberOfCommentsToLabel: Int
    ) : HomeViewState()
}