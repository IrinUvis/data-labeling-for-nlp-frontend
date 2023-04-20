package it.nlp.frontend.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import it.nlp.frontend.ui.core.component.PreviewThemeWithBackground

@Preview
@Composable
fun DarkHomeContentPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        HomeContent(
            viewState = HomeViewState.Loaded(
                numberOfTextsToLabel = 10,
                assignmentsCount = 10
            ),
            onSettingsButtonClicked = { },
            onGoToTextsLabelingClicked = { },
            onNumberOfTextsToLabelUpdated = {},
        )
    }
}

@Preview
@Composable
fun LightHomeContentPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        HomeContent(
            viewState = HomeViewState.Loaded(
                numberOfTextsToLabel = 5,
                assignmentsCount = 10,
            ),
            onSettingsButtonClicked = { },
            onGoToTextsLabelingClicked = { },
            onNumberOfTextsToLabelUpdated = {},
        )
    }
}
