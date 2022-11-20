package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.AppDestination
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.InitialDestination

sealed class SplashViewState(
    open val startDestination: AppDestination,
) {
    object Loading : SplashViewState(InitialDestination)

    data class Completed(override val startDestination: AppDestination) :
        SplashViewState(startDestination)
}
