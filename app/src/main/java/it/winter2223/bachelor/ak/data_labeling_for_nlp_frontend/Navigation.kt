package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.AppDestination.Home.route
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui.CommentLabelingScreen
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui.HomeScreen
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui.LogInScreen
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AppDestination.Splash.route,
    ) {
        composable(AppDestination.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(AppDestination.LogIn.route) {
            LogInScreen(navController = navController)
        }
        composable(AppDestination.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            AppDestination.CommentLabeling.route,
            arguments = listOf(navArgument("commentQuantity") { type = NavType.IntType })
        ) {
            CommentLabelingScreen(navController = navController)
        }
    }
}

sealed class AppDestination(
    val route: String,
) {
    object Splash : AppDestination("splash")

    object LogIn : AppDestination("logIn")

    object Home : AppDestination("home")

    object CommentLabeling : AppDestination("commentlabeling/{commentQuantity}")
}
