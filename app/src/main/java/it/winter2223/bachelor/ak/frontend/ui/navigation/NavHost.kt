package it.winter2223.bachelor.ak.frontend.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import it.winter2223.bachelor.ak.frontend.ui.splash.SplashViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    splashViewModel: SplashViewModel,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = InitialDestination.route,
    ) {
        initialScreen(
            navController = navController,
            splashViewModel = splashViewModel,
        )
        logInScreen(
            navController = navController,
        )
        homeScreen(
            navController = navController,
        )
        settingsScreen(
            navController = navController,
        )
        commentLabelingScreen(
            navController = navController,
        )
    }
}

fun NavOptionsBuilder.popUpToTop() {
    popUpTo(0)
}

sealed class AppDestination(
    val route: String,
)
