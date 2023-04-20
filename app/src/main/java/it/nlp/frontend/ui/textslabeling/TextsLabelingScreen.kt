package it.nlp.frontend.ui.textslabeling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TextsLabelingScreen(
    viewModel: TextsLabelingViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToLogIn: () -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()

    TextsLabelingContent(
        viewState = viewState.value,
        onEmotionSelected = viewModel::onEmotionSelected,
        onPreviousButtonClicked = viewModel::goToPreviousText,
        onNextButtonClicked = viewModel::checkForUnspecifiableEmotionAndGoToNextText,
        onBackButtonClicked = navigateUp,
        onGoToNextText = viewModel::goToNextText,
        onCloseDialog = viewModel::closeDialog,
        onRetryLoading = viewModel::retryLoading,
        onNavigateToLogIn = {
            viewModel.logOut()
            navigateToLogIn()
        }
    )
}
