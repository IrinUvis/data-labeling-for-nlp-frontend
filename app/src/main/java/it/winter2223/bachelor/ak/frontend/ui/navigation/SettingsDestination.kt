package it.winter2223.bachelor.ak.frontend.ui.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import it.winter2223.bachelor.ak.frontend.ui.settings.SettingsScreen

object SettingsDestination : AppDestination("settings")

fun NavGraphBuilder.settingsScreen(
    navController: NavController,
) {
    composable(SettingsDestination.route) {
        SettingsScreen(
            navigateToHome = {
                Log.d("NAV", navController.backQueue.map { it.destination.route }.toString())

                navController.navigateToHome()
            },
            navigateToLogin = {
                Log.d("NAV", navController.backQueue.map { it.destination.route }.toString())
                navController.navigateToLogIn {
                    popUpTo(SettingsDestination.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

fun NavController.navigateToSettings(
    navOptions: NavOptions? = null,
) {
    navigate(SettingsDestination.route, navOptions = navOptions)
}