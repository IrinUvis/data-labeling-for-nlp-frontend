package it.winter2223.bachelor.ak.frontend.ui.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.frontend.domain.theme.model.GetThemeFlowResult
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.GetThemeFlowUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.model.GetTokenFlowResult
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.GetTokenFlowUseCase
import it.winter2223.bachelor.ak.frontend.ui.core.model.UiTheme
import it.winter2223.bachelor.ak.frontend.ui.core.model.toUiTheme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    getTokenFlowUseCase: GetTokenFlowUseCase,
    getThemeFlowUseCase: GetThemeFlowUseCase,
) : ViewModel() {
    companion object {
        private const val THEME_STATEFLOW_STOP_TIMEOUT_MILLIS = 5000L
    }

    private val _viewState: MutableState<SplashViewState> =
        mutableStateOf(SplashViewState.Loading)
    val viewState: State<SplashViewState> = _viewState

    init {
        viewModelScope.launch {
            val loggedIn =
                when (val getTokenResult = getTokenFlowUseCase()) {
                    is GetTokenFlowResult.Success -> {
                        val token = getTokenResult.tokenFlow.first()
                        token != null
                    }
                    is GetTokenFlowResult.Failure -> false
                }

            _viewState.value = SplashViewState.Completed(
                loggedIn = loggedIn,
            )

            when (val getThemeFlowResult = getThemeFlowUseCase()) {
                is GetThemeFlowResult.Success -> {
                    getThemeFlowResult.themeFlow.map {
                        it.toUiTheme()
                    }.stateIn(
                        scope = viewModelScope,
                        initialValue = UiTheme.System,
                        started = SharingStarted.WhileSubscribed(THEME_STATEFLOW_STOP_TIMEOUT_MILLIS)
                    ).collect { theme ->
                        (_viewState.value as? SplashViewState.Completed)?.let {
                            _viewState.value = SplashViewState.Completed(
                                loggedIn = it.loggedIn,
                                theme = theme,
                            )
                        }
                    }
                }
                is GetThemeFlowResult.Failure -> {
                    UiTheme.System
                }
            }
        }
    }
}
