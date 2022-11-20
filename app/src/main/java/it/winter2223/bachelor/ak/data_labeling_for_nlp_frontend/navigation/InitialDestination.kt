package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui.SplashScreen
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui.SplashViewModel
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui.SplashViewState

object InitialDestination : AppDestination("splash")

fun NavGraphBuilder.initialScreen(
    navController: NavController,
    splashViewModel: SplashViewModel,
) {
    composable(InitialDestination.route) {
        val state = splashViewModel.viewState.value
        SplashScreen(
            viewModel = splashViewModel,
            navigateToAppropriateStartingDestination = {
                if (state is SplashViewState.Completed) {
                    val builder: NavOptionsBuilder.() -> Unit = {
                        popUpTo(InitialDestination.route) {
                            inclusive = true
                        }
                    }
                    when (state.loggedIn) {
                        false -> navController.navigateToLogIn(builder)
                        true -> navController.navigateToHome(builder)
                    }
                }
            },
        )
    }
}
