package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SplashDestination.route
    ) {
        splashScreen(
            navController = navController,
        )
        logInScreen(
            navController = navController,
        )
        homeScreen(
            navController = navController,
        )
        commentLabelingScreen(
            navController = navController,
        )
    }
}

sealed class AppDestination(
    val route: String,
)
