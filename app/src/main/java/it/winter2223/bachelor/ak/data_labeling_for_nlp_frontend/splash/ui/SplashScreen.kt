package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.LogInDestination
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.SplashDestination
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.navigateToHome
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.navigateToLogIn

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navController: NavController
) {
    val viewState = viewModel.viewState.collectAsState()

    LaunchedEffect(viewState.value) {
        val state = viewState.value
        if (state is SplashViewState.Completed) {
            val builder: NavOptionsBuilder.() -> Unit = {
                popUpTo(SplashDestination.route) {
                    inclusive = true
                }
            }
            when (state.destination) {
                is LogInDestination -> navController.navigateToLogIn(builder)
                else -> navController.navigateToHome(builder)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
