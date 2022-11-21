package it.winter2223.bachelor.ak.frontend.ui.commentlabeling

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.component.CommentCard
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.component.EmotionSelector
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.component.ProgressSection
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.model.UiEmotion
import it.winter2223.bachelor.ak.frontend.ui.core.component.VerticalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.bigPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.getString
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.mediumPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.smallPadding

@Composable
fun ActiveCommentLabelingContent(
    modifier: Modifier = Modifier,
    viewState: CommentLabelingViewState.Loaded,
    onEmotionSelected: (UiEmotion) -> Unit,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    BackHandler(
        enabled = viewState.currentCommentIndex != 0,
        onBack = onPreviousButtonClicked,
    )

    Column(
        modifier = modifier
            .padding(
                start = mediumPadding,
                end = mediumPadding,
                top = smallPadding,
                bottom = bigPadding,
            )
            .fillMaxSize()
    ) {
        CommentCard(
            modifier = Modifier.weight(1f),
            scrollState = rememberScrollState(),
            text = viewState.currentComment.text.getString(),
        )

        VerticalSpacer(height = mediumPadding)

        EmotionSelector(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            selectedEmotion = viewState.currentComment.emotion,
            onEmotionSelected = onEmotionSelected,
        )

        VerticalSpacer(height = mediumPadding)

        ProgressSection(
            modifier = Modifier.fillMaxWidth(),
            currentCommentIndex = viewState.currentCommentIndex,
            currentCommentEmotion = viewState.currentComment.emotion,
            progress = viewState.progress,
            onPreviousButtonClicked = onPreviousButtonClicked,
            onNextButtonClicked = onNextButtonClicked,
        )
    }
}
