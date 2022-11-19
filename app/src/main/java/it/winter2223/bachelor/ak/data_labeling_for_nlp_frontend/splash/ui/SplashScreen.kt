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
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.AppDestination

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToAppropriateStartingDestination: (AppDestination) -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()

    LaunchedEffect(viewState.value) {
        val state = viewState.value
        if (state is SplashViewState.Completed) {
            navigateToAppropriateStartingDestination(state.destination)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
