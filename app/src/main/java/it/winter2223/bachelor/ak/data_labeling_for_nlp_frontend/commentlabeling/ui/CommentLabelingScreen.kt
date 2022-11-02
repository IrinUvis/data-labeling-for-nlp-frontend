package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun CommentLabelingScreen(
    viewModel: CommentLabelingViewModel,
) {
    val viewState = viewModel.viewState.collectAsState()

    CommentLabelingContent(
        viewState = viewState.value,
        onEmotionSelected = viewModel::onEmotionSelected,
        onNextButtonClicked = viewModel::goToNextComment,
        onBackButtonClicked = { /* TODO */ },
        onSettingsButtonClicked = { /* TODO */ },
    )
}
