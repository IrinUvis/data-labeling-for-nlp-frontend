package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.data.model.Emotion
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.bigPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.components.VerticalSpacer
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.mediumPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.smallPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveCommentLabelingContent(
    viewState: CommentLabelingViewState.Active,
    onEmotionSelected: (Emotion) -> Unit,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onSettingButtonClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            CommentLabelingTopBar(
                onBackButtonClicked = onBackButtonClicked,
                onSettingsButtonClicked = onSettingButtonClicked,
            )
        },
    ) { paddingValues ->
        ScaffoldBody(
            modifier = Modifier
                .padding(paddingValues)
                .padding(
                    start = mediumPadding,
                    end = mediumPadding,
                    top = smallPadding,
                    bottom = bigPadding,
                )
                .fillMaxSize(),
            viewState = viewState,
            onEmotionSelected = onEmotionSelected,
            onPreviousButtonClicked = onPreviousButtonClicked,
            onNextButtonClicked = onNextButtonClicked,
        )
    }
}

@Composable
fun ScaffoldBody(
    modifier: Modifier = Modifier,
    viewState: CommentLabelingViewState.Active,
    onEmotionSelected: (Emotion) -> Unit,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        CommentCard(
            modifier = Modifier.weight(1f),
            scrollState = rememberScrollState(),
            text = viewState.currentComment.text,
        )

        VerticalSpacer(height = mediumPadding)

        EmotionSelector(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            currentComment = viewState.currentComment,
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
