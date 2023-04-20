package it.nlp.frontend.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit,
    navigateToTextsLabeling: (Int) -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()

    when (val stateValue = viewState.value) {
        is HomeViewState.Loaded -> {
            HomeContent(
                viewState = stateValue,
                onSettingsButtonClicked = {
                    navigateToSettings()
                },
                onGoToTextsLabelingClicked = {
                    navigateToTextsLabeling(stateValue.numberOfTextsToLabel)
                },
                onNumberOfTextsToLabelUpdated = viewModel::updateNumberOfTextsToLabel
            )
        }
    }
}
