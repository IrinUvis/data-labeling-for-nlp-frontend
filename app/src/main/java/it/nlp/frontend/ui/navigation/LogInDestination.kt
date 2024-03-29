package it.nlp.frontend.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import it.nlp.frontend.ui.login.LogInScreen

object LogInDestination : AppDestination("logIn")

fun NavGraphBuilder.logInScreen(
    navController: NavController,
) {
    composable(LogInDestination.route) {
        LogInScreen(
            navigateToHome = {
                navController.navigateToHome {
                    popUpTo(LogInDestination.route) {
                        inclusive = true
                    }
                }
            },
        )
    }
}

fun NavController.navigateToLogIn(
    navOptions: NavOptions? = null,
) {
    navigate(LogInDestination.route, navOptions = navOptions)
}

fun NavController.navigateToLogIn(
    builder: (NavOptionsBuilder.() -> Unit),
) {
    navigateToLogIn(
        navOptions = navOptions(builder)
    )
}
