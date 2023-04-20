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

    HomeContent(
        viewState = viewState.value,
        onSettingsButtonClicked = {
            navigateToSettings()
        },
        onGoToTextsLabelingClicked = {
            navigateToTextsLabeling(viewState.value.numberOfTextsToLabel)
        },
        onNumberOfTextsToLabelUpdated = viewModel::updateNumberOfTextsToLabel
    )
}
