@file:Suppress("UnusedPrivateMember", "TooManyFunctions")

package it.winter2223.bachelor.ak.frontend.ui.commentlabeling

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.component.CommentCard
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.component.CommentLabelingTopBar
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.component.EmotionSelector
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.component.ProgressSection
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.model.UiComment
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.model.UiEmotion
import it.winter2223.bachelor.ak.frontend.ui.core.component.PreviewThemeWithBackground
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.UiText

private const val NUMBER_OF_COMMENTS_FOR_PREVIEWS = 5

@Preview(name = "Dark themed Active Comment Labeling Content", showBackground = true)
@Composable
private fun DarkActiveCommentLabelingContentPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        ActiveCommentLabelingContent(
            viewState = CommentLabelingViewState.Loaded.Active(
                comments = List(NUMBER_OF_COMMENTS_FOR_PREVIEWS) {
                    UiComment(
                        text = UiText.StringText(stringResource(id = R.string.shortPreviewText)),
                        emotion = UiEmotion.Love,
                    )
                },
                currentCommentIndex = 2,
            ),
            onEmotionSelected = { },
            onPreviousButtonClicked = { },
            onNextButtonClicked = { },
        )
    }
}

@Preview(name = "Light themed Active Comment Labeling Content", showBackground = true)
@Composable
private fun LightActiveCommentLabelingContentPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        ActiveCommentLabelingContent(
            viewState = CommentLabelingViewState.Loaded.Active(
                comments = List(NUMBER_OF_COMMENTS_FOR_PREVIEWS) {
                    UiComment(
                        text = UiText.StringText(stringResource(id = R.string.shortPreviewText)),
                        emotion = UiEmotion.Love,
                    )
                },
                currentCommentIndex = 2,
            ),
            onEmotionSelected = { },
            onPreviousButtonClicked = { },
            onNextButtonClicked = { },
        )
    }
}

@Preview(name = "Dark themed Loading Comment Labeling Content", showBackground = true)
@Composable
private fun DarkLoadingCommentLabelingContentPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        LoadingCommentLabelingContent(
            text = stringResource(R.string.loadingComments),
        )
    }
}

@Preview(name = "Light themed Loading Comment Labeling Content", showBackground = true)
@Composable
private fun LightLoadingCommentLabelingContentPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        LoadingCommentLabelingContent(
            text = stringResource(R.string.loadingComments)
        )
    }
}

@Preview(name = "Dark themed Top app bar", showBackground = true)
@Composable
private fun DarkCommentLabelingTopBarPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        CommentLabelingTopBar(
            onBackButtonClicked = { },
        )
    }
}

@Preview(name = "Light themed Top app bar", showBackground = true)
@Composable
private fun LightCommentLabelingTopBarPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        CommentLabelingTopBar(
            onBackButtonClicked = { },
        )
    }
}

@Preview(
    name = "Dark themed Comment Card with short text", showBackground = true,
    widthDp = 250,
    heightDp = 150,
)
@Composable
private fun DarkCommentCardWithShortTextPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        CommentCard(
            scrollState = rememberScrollState(),
            text = stringResource(R.string.shortPreviewText),
        )
    }
}

@Preview(
    name = "Dark themed Comment Card with long text",
    showBackground = true,
    widthDp = 250,
    heightDp = 150,
)
@Composable
private fun DarkCommentCardWithLongTextPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        CommentCard(
            scrollState = rememberScrollState(),
            text = stringResource(R.string.longPreviewText),
        )
    }
}

@Preview(
    name = "Light themed Comment Card with short text", showBackground = true,
    widthDp = 250,
    heightDp = 150,
)
@Composable
private fun LightCommentCardWithShortTextPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        CommentCard(
            scrollState = rememberScrollState(),
            text = stringResource(R.string.shortPreviewText),
        )
    }
}

@Preview(
    name = "Light themed Comment Card with long text", showBackground = true,
    widthDp = 250,
    heightDp = 150,
)
@Composable
private fun LightCommentCardWithLongTextPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        CommentCard(
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
            currentCommentIndex = 0,
            currentCommentEmotion = null,
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
            currentCommentIndex = 1,
            currentCommentEmotion = UiEmotion.Fear,
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
            currentCommentIndex = 0,
            currentCommentEmotion = null,
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
            currentCommentIndex = 1,
            currentCommentEmotion = UiEmotion.Fear,
            progress = 0.25f,
            onPreviousButtonClicked = { },
            onNextButtonClicked = { },
        )
    }
}


