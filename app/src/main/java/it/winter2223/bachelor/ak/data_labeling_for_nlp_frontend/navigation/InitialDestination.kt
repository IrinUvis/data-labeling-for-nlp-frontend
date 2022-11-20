package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui.SplashScreen


object InitialDestination : AppDestination("splash")

fun NavGraphBuilder.initialScreen(
    navController: NavController,
    startDestination: AppDestination,
) {
    composable(InitialDestination.route) {
        SplashScreen(
            navigateToAppropriateStartingDestination = {
                val builder: NavOptionsBuilder.() -> Unit = {
                    popUpTo(InitialDestination.route) {
                        inclusive = true
                    }
                }
                Log.d("NAV", startDestination.route)
                when (startDestination) {
                    is LogInDestination -> navController.navigateToLogIn(builder)
                    else -> navController.navigateToHome(builder)
                }
            },
        )
    }
}
