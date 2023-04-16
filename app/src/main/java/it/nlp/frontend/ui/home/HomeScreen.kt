package it.nlp.frontend.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit,
    navigateToCommentLabeling: (Int) -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()

    when (val stateValue = viewState.value) {
        is HomeViewState.Loaded -> {
            HomeContent(
                viewState = stateValue,
                onSettingsButtonClicked = {
                    navigateToSettings()
                },
                onGoToCommentLabelingClicked = {
                    navigateToCommentLabeling(stateValue.numberOfCommentsToLabel)
                },
                onNumberOfCommentsToLabelUpdated = viewModel::updateNumberOfCommentsToLabel
            )
        }
    }
}
