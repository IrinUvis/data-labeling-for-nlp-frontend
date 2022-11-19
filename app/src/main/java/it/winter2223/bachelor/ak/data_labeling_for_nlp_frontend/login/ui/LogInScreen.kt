package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LogInScreen(
    viewModel: LogInViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()

    LaunchedEffect(viewState.value) {
        val state = viewState.value
        if (state is LogInViewState.Completed) {
            navigateToHome()
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
