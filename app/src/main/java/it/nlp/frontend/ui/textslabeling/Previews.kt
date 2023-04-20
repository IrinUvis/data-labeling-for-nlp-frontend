@file:Suppress("UnusedPrivateMember", "TooManyFunctions")

package it.nlp.frontend.ui.textslabeling

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import it.nlp.frontend.R
import it.nlp.frontend.ui.textslabeling.component.TextCard
import it.nlp.frontend.ui.textslabeling.component.TextsLabelingTopBar
import it.nlp.frontend.ui.textslabeling.component.EmotionSelector
import it.nlp.frontend.ui.textslabeling.component.ProgressSection
import it.nlp.frontend.ui.textslabeling.model.UiEmotionText
import it.nlp.frontend.ui.textslabeling.model.UiEmotion
import it.nlp.frontend.ui.core.component.PreviewThemeWithBackground
import it.nlp.frontend.ui.core.helpers.UiText

private const val NUMBER_OF_TEXTS_FOR_PREVIEWS = 5

@Preview(name = "Dark themed Active Texts Labeling Content", showBackground = true)
@Composable
private fun DarkActiveTextsLabelingContentPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        TextsLabelingActiveContent(
            viewState = TextsLabelingViewState.Loaded.Active(
                texts = List(NUMBER_OF_TEXTS_FOR_PREVIEWS) {
                    UiEmotionText(
                        id = "randomId",
                        text = UiText.StringText(stringResource(id = R.string.shortPreviewText)),
                        emotion = UiEmotion.Love,
                    )
                },
                currentTextIndex = 2,
            ),
            onEmotionSelected = { },
            onPreviousButtonClicked = { },
            onNextButtonClicked = { },
            onGoToNextText = { },
            onCloseDialog = { },
        )
    }
}

@Preview(name = "Light themed Active Texts Labeling Content", showBackground = true)
@Composable
private fun LightActiveTextsLabelingContentPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        TextsLabelingActiveContent(
            viewState = TextsLabelingViewState.Loaded.Active(
                texts = List(NUMBER_OF_TEXTS_FOR_PREVIEWS) {
                    UiEmotionText(
                        id = "randomId",
                        text = UiText.StringText(stringResource(id = R.string.shortPreviewText)),
                        emotion = UiEmotion.Love,
                    )
                },
                currentTextIndex = 2,
            ),
            onEmotionSelected = { },
            onPreviousButtonClicked = { },
            onNextButtonClicked = { },
            onGoToNextText = { },
            onCloseDialog = { },
        )
    }
}

@Preview(name = "Dark themed Loading Texts Labeling Content", showBackground = true)
@Composable
private fun DarkLoadingTextsLabelingContentPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        LoadingTextsLabelingContent(
            text = stringResource(R.string.loadingComments),
        )
    }
}

@Preview(name = "Light themed Loading Texts Labeling Content", showBackground = true)
@Composable
private fun LightLoadingTextsLabelingContentPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        LoadingTextsLabelingContent(
            text = stringResource(R.string.loadingComments)
        )
    }
}

@Preview
@Composable
private fun DarkLoadingErrorTextsLabelingContentPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        LoadingErrorTextsLabelingContent(
            errorMessage = stringResource(R.string.shortPreviewText),
            onRetryLoading = { }
        )
    }
}

@Preview
@Composable
private fun LightLoadingErrorTextsLabelingContentPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        LoadingErrorTextsLabelingContent(
            errorMessage = stringResource(R.string.shortPreviewText),
            onRetryLoading = { }
        )
    }
}

@Preview(name = "Dark themed Top app bar", showBackground = true)
@Composable
private fun DarkTextsLabelingTopBarPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        TextsLabelingTopBar(
            onBackButtonClicked = { },
        )
    }
}

@Preview(name = "Light themed Top app bar", showBackground = true)
@Composable
private fun LightTextsLabelingTopBarPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        TextsLabelingTopBar(
            onBackButtonClicked = { },
        )
    }
}

@Preview(
    name = "Dark themed Text Card with short text", showBackground = true,
    widthDp = 250,
    heightDp = 150,
)
@Composable
private fun DarkTextCardWithShortTextPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        TextCard(
            scrollState = rememberScrollState(),
            text = stringResource(R.string.shortPreviewText),
        )
    }
}

@Preview(
    name = "Dark themed Text Card with long text",
    showBackground = true,
    widthDp = 250,
    heightDp = 150,
)
@Composable
private fun DarkTextCardWithLongTextPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        TextCard(
            scrollState = rememberScrollState(),
            text = stringResource(R.string.longPreviewText),
        )
    }
}

@Preview(
    name = "Light themed Text Card with short text", showBackground = true,
    widthDp = 250,
    heightDp = 150,
)
@Composable
private fun LightTextCardWithShortTextPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        TextCard(
            scrollState = rememberScrollState(),
            text = stringResource(R.string.shortPreviewText),
        )
    }
}

@Preview(
    name = "Light themed Text Card with long text", showBackground = true,
    widthDp = 250,
    heightDp = 150,
)
@Composable
private fun LightTextCardWithLongTextPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        TextCard(
            scrollState = rememberScrollState(),
            text = stringResource(R.string.longPreviewText),
        )
    }
}

@Preview(
    name = "Dark themed Emotion selector without emotion selected", showBackground = true
)
@Composable
private fun DarkEmotionSelectorWithoutEmotionSelectedPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        EmotionSelector(
            selectedEmotion = null,
            onEmotionSelected = { },
        )
    }
}

@Preview(
    name = "Dark themed Emotion selector with emotion selected", showBackground = true
)
@Composable
private fun DarkEmotionSelectorWithEmotionSelectedPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        EmotionSelector(
            selectedEmotion = UiEmotion.Love,
            onEmotionSelected = { },
        )
    }
}

@Preview(
    name = "Light themed Emotion selector without emotion selected", showBackground = true
)
@Composable
private fun LightEmotionSelectorWithoutEmotionSelectedPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        EmotionSelector(
            selectedEmotion = null,
            onEmotionSelected = { },
        )
    }
}

@Preview(
    name = "Light themed Emotion selector with emotion selected", showBackground = true
)
@Composable
private fun LightEmotionSelectorWithEmotionSelectedPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        EmotionSelector(
            selectedEmotion = UiEmotion.Love,
            onEmotionSelected = { },
        )
    }
}

@Preview(
    name = "Dark themed progress section for first element without selected emotion",
    showBackground = true,
)
@Composable
private fun DarkProgressSectionForFirstElementWithoutSelectedEmotionPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        ProgressSection(
            currentTextIndex = 0,
            currentTextEmotion = null,
            progress = 0f,
            onPreviousButtonClicked = { },
            onNextButtonClicked = { },
        )
    }
}

@Preview(
    name = "Dark themed progress section for second element with selected emotion",
    showBackground = true,
)
@Composable
private fun DarkProgressSectionForSecondElementWithSelectedEmotionPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        ProgressSection(
            currentTextIndex = 1,
            currentTextEmotion = UiEmotion.Fear,
            progress = 0.25f,
            onPreviousButtonClicked = { },
            onNextButtonClicked = { },
        )
    }
}

@Preview(
    name = "Light themed progress section for first element without selected emotion",
    showBackground = true,
)
@Composable
private fun LightProgressSectionForFirstElementWithoutSelectedEmotionPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        ProgressSection(
            currentTextIndex = 0,
            currentTextEmotion = null,
            progress = 0f,
            onPreviousButtonClicked = { },
            onNextButtonClicked = { },
        )
    }
}

@Preview(
    name = "Light themed progress section for second element with selected emotion",
    showBackground = true,
)
@Composable
private fun LightProgressSectionForSecondElementWithSelectedEmotionPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        ProgressSection(
            currentTextIndex = 1,
            currentTextEmotion = UiEmotion.Fear,
            progress = 0.25f,
            onPreviousButtonClicked = { },
            onNextButtonClicked = { },
        )
    }
}


