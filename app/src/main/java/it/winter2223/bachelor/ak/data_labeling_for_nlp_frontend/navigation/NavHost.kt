package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: AppDestination,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = InitialDestination.route,
    ) {
        initialScreen(
            navController = navController,
            startDestination = startDestination,
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
