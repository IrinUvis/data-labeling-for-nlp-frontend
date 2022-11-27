package it.winter2223.bachelor.ak.frontend.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import it.winter2223.bachelor.ak.frontend.ui.core.component.PreviewThemeWithBackground

@Preview
@Composable
fun DarkHomeContentPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        HomeContent(
            viewState = HomeViewState.Loaded(
                numberOfCommentsToLabel = 10,
            ),
            onSettingsButtonClicked = { },
            onGoToCommentLabelingClicked = { },
            onNumberOfCommentsToLabelUpdated = {},
        )
    }
}

@Preview
@Composable
fun LightHomeContentPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        HomeContent(
            viewState = HomeViewState.Loaded(
                numberOfCommentsToLabel = 5,
            ),
            onSettingsButtonClicked = { },
            onGoToCommentLabelingClicked = { },
            onNumberOfCommentsToLabelUpdated = {},
        )
    }
}
