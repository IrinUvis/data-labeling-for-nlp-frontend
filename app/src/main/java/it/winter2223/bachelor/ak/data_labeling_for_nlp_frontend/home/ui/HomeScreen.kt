package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.navigateToCommentLabeling

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val viewState = viewModel.viewState.collectAsState()

    // TODO: Remove magic number
    val commentsQuantity = (viewState.value as? HomeViewState.Loaded)?.numberOfCommentsToLabel ?: 1

    HomeContent(
        viewState = viewState.value,
        onLogOutButtonClicked = {
            viewModel.logOut(navController)
        },
        onGoToCommentLabelingClicked = { navController.navigateToCommentLabeling(commentsQuantity) },
        onNumberOfCommentsToLabelUpdated = viewModel::updateNumberOfCommentsToLabel
    )
}
