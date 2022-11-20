package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.GetTokenFlowResult
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.GetTokenFlowUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.HomeDestination
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.LogInDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    getTokenFlowUseCase: GetTokenFlowUseCase,
) : ViewModel() {
    private val _viewState: MutableState<SplashViewState> =
        mutableStateOf(SplashViewState.Loading)
    val viewState: State<SplashViewState> = _viewState

    init {
        Log.d("SPLASHVM", "created")
        viewModelScope.launch {
            val startingDestination = when (val getTokenResult = getTokenFlowUseCase()) {
                is GetTokenFlowResult.Success -> {
                    val token = getTokenResult.tokenFlow.first()
                    if (token != null) HomeDestination else LogInDestination
                }
                is GetTokenFlowResult.Failure -> LogInDestination
            }

            _viewState.value = SplashViewState.Completed(startDestination = startingDestination)
        }
    }
}
