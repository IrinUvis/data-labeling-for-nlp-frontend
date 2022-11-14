@file:Suppress("UnusedPrivateMember", "TooManyFunctions")

package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.R
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.data.model.Comment
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.data.model.Emotion
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.theme.AppTheme

private const val NUMBER_OF_COMMENTS_FOR_PREVIEWS = 5

@Preview(name = "Dark themed Active Comment Labeling Content", showBackground = true)
@Composable
private fun DarkActiveCommentLabelingContent() {
    PreviewThemeWithBackground(darkTheme = true) {
        ActiveCommentLabelingContent(
            viewState = CommentLabelingViewState.Active(
                comments = List(NUMBER_OF_COMMENTS_FOR_PREVIEWS) {
                    Comment(
                        text = stringResource(id = R.string.shortPreviewText),
                        emotion = Emotion.Love,
                    )
                },
                currentCommentIndex = 2,
            ),
            onEmotionSelected = { },
            onPreviousButtonClicked = { },
            onNextButtonClicked = { },
            onBackButtonClicked = { },
            onSettingButtonClicked = { },
        )
    }
}

@Preview(name = "Light themed Active Comment Labeling Content", showBackground = true)
@Composable
private fun LightActiveCommentLabelingContent() {
    PreviewThemeWithBackground(darkTheme = false) {
        ActiveCommentLabelingContent(
            viewState = CommentLabelingViewState.Active(
                comments = List(NUMBER_OF_COMMENTS_FOR_PREVIEWS) {
                    Comment(
                        text = stringResource(id = R.string.shortPreviewText),
                        emotion = Emotion.Love,
                    )
                },
                currentCommentIndex = 2,
            ),
            onEmotionSelected = { },
            onPreviousButtonClicked = { },
            onNextButtonClicked = { },
            onBackButtonClicked = { },
            onSettingButtonClicked = { },
        )
    }
}

@Preview(name = "Dark themed Loading Comment Labeling Content", showBackground = true)
@Composable
private fun DarkLoadingCommentLabelingContent() {
    PreviewThemeWithBackground(darkTheme = true) {
        LoadingCommentLabelingContent()
    }
}

@Preview(name = "Light themed Loading Comment Labeling Content", showBackground = true)
@Composable
private fun LightLoadingCommentLabelingContent() {
    PreviewThemeWithBackground(darkTheme = false) {
        LoadingCommentLabelingContent()
    }
}

@Preview(name = "Dark themed Top app bar", showBackground = true)
@Composable
private fun DarkCommentLabelingTopBar() {
    PreviewThemeWithBackground(darkTheme = true) {
        CommentLabelingTopBar(
            onBackButtonClicked = { },
            onSettingsButtonClicked = { },
        )
    }
}

@Preview(name = "Light themed Top app bar", showBackground = true)
@Composable
private fun LightCommentLabelingTopBar() {
    PreviewThemeWithBackground(darkTheme = false) {
        CommentLabelingTopBar(
            onBackButtonClicked = { },
            onSettingsButtonClicked = { },
        )
    }
}

@Preview(
    name = "Dark themed Comment Card with short text", showBackground = true,
    widthDp = 250,
    heightDp = 150,
)
@Composable
private fun DarkCommentCardWithShortText() {
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
private fun DarkCommentCardWithLongText() {
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
private fun LightCommentCardWithShortText() {
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
private fun LightCommentCardWithLongText() {
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
private fun DarkEmotionSelectorWithoutEmotionSelected() {
    PreviewThemeWithBackground(darkTheme = true) {
        EmotionSelector(
            currentComment = Comment(text = stringResource(R.string.shortPreviewText)),
            onEmotionSelected = { },
        )
    }
}

@Preview(
    name = "Dark themed Emotion selector with emotion selected", showBackground = true
)
@Composable
private fun DarkEmotionSelectorWithEmotionSelected() {
    PreviewThemeWithBackground(darkTheme = true) {
        EmotionSelector(
            currentComment = Comment(
                text = stringResource(R.string.shortPreviewText),
                emotion = Emotion.Fear,
            ),
            onEmotionSelected = { },
        )
    }
}

@Preview(
    name = "Light themed Emotion selector without emotion selected", showBackground = true
)
@Composable
private fun LightEmotionSelectorWithoutEmotionSelected() {
    PreviewThemeWithBackground(darkTheme = false) {
        EmotionSelector(
            currentComment = Comment(text = stringResource(R.string.shortPreviewText)),
            onEmotionSelected = { },
        )
    }
}

@Preview(
    name = "Light themed Emotion selector with emotion selected", showBackground = true
)
@Composable
private fun LightEmotionSelectorWithEmotionSelected() {
    PreviewThemeWithBackground(darkTheme = false) {
        EmotionSelector(
            currentComment = Comment(
                text = stringResource(R.string.shortPreviewText),
                emotion = Emotion.Fear,
            ),
            onEmotionSelected = { },
        )
    }
}

@Preview(
    name = "Dark themed progress section for first element without selected emotion",
    showBackground = true,
)
@Composable
private fun DarkProgressSectionForFirstElementWithoutSelectedEmotion() {
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
private fun DarkProgressSectionForSecondElementWithSelectedEmotion() {
    PreviewThemeWithBackground(darkTheme = true) {
        ProgressSection(
            currentCommentIndex = 1,
            currentCommentEmotion = Emotion.Fear,
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
private fun LightProgressSectionForFirstElementWithoutSelectedEmotion() {
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
private fun LightProgressSectionForSecondElementWithSelectedEmotion() {
    PreviewThemeWithBackground(darkTheme = false) {
        ProgressSection(
            currentCommentIndex = 1,
            currentCommentEmotion = Emotion.Fear,
            progress = 0.25f,
            onPreviousButtonClicked = { },
            onNextButtonClicked = { },
        )
    }
}

@Composable
private fun PreviewThemeWithBackground(darkTheme: Boolean, content: @Composable () -> Unit) {
    AppTheme(
        darkTheme = darkTheme,
    ) {
        Surface(color = MaterialTheme.colorScheme.background, content = content)
    }
}
