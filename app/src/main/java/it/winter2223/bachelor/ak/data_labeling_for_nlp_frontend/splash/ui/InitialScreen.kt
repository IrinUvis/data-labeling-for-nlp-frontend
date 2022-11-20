package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.AppDestination

@Composable
fun SplashScreen(
//    viewModel: SplashViewModel = hiltViewModel(),
    navigateToAppropriateStartingDestination: () -> Unit,
) {
//    val viewState = viewModel.viewState

    LaunchedEffect(true) {
//        val state = viewState.value
//        if (state is SplashViewState.Completed) {
//            navigateToAppropriateStartingDestination()
//        }
        navigateToAppropriateStartingDestination()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    )
}
