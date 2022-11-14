package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CommentLabelingScreen(
    viewModel: CommentLabelingViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()

    CommentLabelingContent(
        viewState = viewState.value,
        onEmotionSelected = viewModel::onEmotionSelected,
        onPreviousButtonClicked = viewModel::goToPreviousComment,
        onNextButtonClicked = viewModel::goToNextComment,
        onBackButtonClicked = { /* TODO */ },
        onSettingsButtonClicked = { /* TODO */ },
    )
}
