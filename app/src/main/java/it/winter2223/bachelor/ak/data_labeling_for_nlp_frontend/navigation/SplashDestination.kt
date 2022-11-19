package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui.SplashScreen


object SplashDestination : AppDestination("splash")

fun NavGraphBuilder.splashScreen(navController: NavController) {
    composable(SplashDestination.route) {
        SplashScreen(
            navigateToAppropriateStartingDestination = { startingDestination ->
                val builder: NavOptionsBuilder.() -> Unit = {
                    popUpTo(SplashDestination.route) {
                        inclusive = true
                    }
                }
                when (startingDestination) {
                    is LogInDestination -> navController.navigateToLogIn(builder)
                    else -> navController.navigateToHome(builder)
                }
            },
        )
    }
}
