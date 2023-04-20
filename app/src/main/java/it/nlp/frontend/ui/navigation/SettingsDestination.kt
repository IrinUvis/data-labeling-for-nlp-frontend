package it.nlp.frontend.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import it.nlp.frontend.ui.settings.SettingsScreen

object SettingsDestination : AppDestination("settings")

fun NavGraphBuilder.settingsScreen(
    navController: NavController,
) {
    composable(SettingsDestination.route) {
        SettingsScreen(
            navigateUp = {
                navController.navigateUp()
            },
            navigateToLogin = {
                navController.navigateToLogIn {
                    popUpToTop()
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
