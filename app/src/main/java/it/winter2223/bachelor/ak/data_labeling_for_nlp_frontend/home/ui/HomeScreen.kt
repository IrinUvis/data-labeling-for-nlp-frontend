package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val viewState = viewModel.viewState.collectAsState()

    HomeContent(
        viewState = viewState.value,
        onLogOutButtonClicked = {
            viewModel.logOut(navController)
        },
        onGoToCommentLabelingClicked = { navController.navigate("commentlabeling/5") },
        onNumberOfCommentsToLabelUpdated = viewModel::updateNumberOfCommentsToLabel
    )
}