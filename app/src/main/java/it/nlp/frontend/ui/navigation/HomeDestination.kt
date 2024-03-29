package it.nlp.frontend.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import it.nlp.frontend.ui.home.HomeScreen

object HomeDestination : AppDestination("home")

fun NavGraphBuilder.homeScreen(
    navController: NavController,
) {
    composable(HomeDestination.route) {
        HomeScreen(
            navigateToSettings = {
                navController.navigateToSettings()
            },
            navigateToCommentLabeling = { quantity ->
                navController.navigateToCommentLabeling(quantity)
            },
        )
    }
}

fun NavController.navigateToHome(
    navOptions: NavOptions? = null,
) {
    navigate(HomeDestination.route, navOptions = navOptions)
}

fun NavController.navigateToHome(
    builder: (NavOptionsBuilder.() -> Unit),
) {
    navigateToHome(
        navOptions = navOptions(builder)
    )
}
