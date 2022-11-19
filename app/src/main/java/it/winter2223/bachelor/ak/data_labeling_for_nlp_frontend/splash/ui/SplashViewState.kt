package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.AppDestination

sealed class SplashViewState {
    object Loading : SplashViewState()

    data class Completed(val destination: AppDestination) : SplashViewState()
}
