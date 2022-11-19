package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.HomeDestination
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.navigateToCommentLabeling
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.navigateToLogIn

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val viewState = viewModel.viewState.collectAsState()

    when (val stateValue = viewState.value) {
        is HomeViewState.Loaded -> {
            HomeContent(
                viewState = stateValue,
                onLogOutButtonClicked = {
                    viewModel.logOut()
                    navController.navigateToLogIn {
                        popUpTo(HomeDestination.route) {
                            inclusive = true
                        }
                    }
                },
                onGoToCommentLabelingClicked = {
                    navController.navigateToCommentLabeling(stateValue.numberOfCommentsToLabel)
                },
                onNumberOfCommentsToLabelUpdated = viewModel::updateNumberOfCommentsToLabel
            )
        }
    }
}
