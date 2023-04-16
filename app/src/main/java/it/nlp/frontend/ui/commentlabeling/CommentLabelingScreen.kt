package it.nlp.frontend.ui.commentlabeling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CommentLabelingScreen(
    viewModel: CommentLabelingViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToLogIn: () -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()

    CommentLabelingContent(
        viewState = viewState.value,
        onEmotionSelected = viewModel::onEmotionSelected,
        onPreviousButtonClicked = viewModel::goToPreviousComment,
        onNextButtonClicked = viewModel::checkForUnspecifiableEmotionAndGoToNextComment,
        onBackButtonClicked = navigateUp,
        onGoToNextComment = viewModel::goToNextComment,
        onCloseDialog = viewModel::closeDialog,
        onRetryLoading = viewModel::retryLoading,
        onNavigateToLogIn = {
            viewModel.logOut()
            navigateToLogIn()
        }
    )
}
