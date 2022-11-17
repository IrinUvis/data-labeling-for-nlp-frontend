package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.AppDestination
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    tokenRepository: TokenRepository,
) : ViewModel() {
    private val _viewState: MutableStateFlow<SplashViewState> =
        MutableStateFlow(SplashViewState.Loading)
    val viewState: StateFlow<SplashViewState> = _viewState

    init {
        viewModelScope.launch {
            // TODO: To be removed later on
            tokenRepository.clearToken()
            val token = tokenRepository.tokenFlow().first()
            val startingDestination = token?.let {
                AppDestination.CommentLabeling
            } ?: AppDestination.LogIn

            _viewState.value = SplashViewState.Completed(destination = startingDestination)
        }
    }
}
