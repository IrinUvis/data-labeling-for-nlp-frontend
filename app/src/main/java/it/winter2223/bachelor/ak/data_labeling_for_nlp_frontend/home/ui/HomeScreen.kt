package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToLogIn: () -> Unit,
    navigateToCommentLabeling: (Int) -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()

    when (val stateValue = viewState.value) {
        is HomeViewState.Loaded -> {
            HomeContent(
                viewState = stateValue,
                onLogOutButtonClicked = {
                    viewModel.logOut()
                    navigateToLogIn()
                },
                onGoToCommentLabelingClicked = {
                    navigateToCommentLabeling(stateValue.numberOfCommentsToLabel)
                },
                onNumberOfCommentsToLabelUpdated = viewModel::updateNumberOfCommentsToLabel
            )
        }
    }
}
