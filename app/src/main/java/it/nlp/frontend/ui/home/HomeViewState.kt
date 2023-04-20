package it.nlp.frontend.ui.home

sealed class HomeViewState(
    open val numberOfTextsToLabel: Int,
) {
    data class Loaded(
        override val numberOfTextsToLabel: Int,
        val assignmentsCount: Int,
    ) : HomeViewState(
        numberOfTextsToLabel = numberOfTextsToLabel,
    )

    data class Loading(
        override val numberOfTextsToLabel: Int
    ) : HomeViewState(
        numberOfTextsToLabel = numberOfTextsToLabel,
    )
}
