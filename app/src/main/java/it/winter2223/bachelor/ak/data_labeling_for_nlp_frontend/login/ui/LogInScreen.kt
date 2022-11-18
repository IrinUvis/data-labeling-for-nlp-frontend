package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.LogInDestination
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.navigateToHome

@Composable
fun LogInScreen(
    viewModel: LogInViewModel = hiltViewModel(),
    navController: NavController,
) {
    val viewState = viewModel.viewState.collectAsState()

    LaunchedEffect(viewState.value) {
        val state = viewState.value
        if (state is LogInViewState.Completed) {
            navController.navigateToHome {
                popUpTo(LogInDestination.route) {
                    inclusive = true
                }
            }
        }
    }

    LogInContent(
        viewState = viewState.value,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onLogInClicked = viewModel::onLogInPressed,
        onSignUpClicked = viewModel::onSignUpPressed,
    )
}
