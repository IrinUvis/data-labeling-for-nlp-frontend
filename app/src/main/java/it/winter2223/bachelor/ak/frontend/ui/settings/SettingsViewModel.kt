package it.winter2223.bachelor.ak.frontend.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.frontend.domain.theme.model.GetThemeFlowResult
import it.winter2223.bachelor.ak.frontend.domain.theme.model.SavePreferredThemeResult
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.GetThemeFlowUseCase
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.SavePreferredThemeUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.ClearTokenUseCase
import it.winter2223.bachelor.ak.frontend.ui.core.model.UiTheme
import it.winter2223.bachelor.ak.frontend.ui.core.model.toDomainTheme
import it.winter2223.bachelor.ak.frontend.ui.core.model.toUiTheme
import it.winter2223.bachelor.ak.frontend.ui.settings.model.NotificationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val clearTokenUseCase: ClearTokenUseCase,
    private val savePreferredThemeUseCase: SavePreferredThemeUseCase,
    private val getThemeFlowUseCase: GetThemeFlowUseCase,
) : ViewModel() {
    private val _viewState: MutableStateFlow<SettingsViewState> =
        MutableStateFlow(SettingsViewState.Loading)
    val viewState: StateFlow<SettingsViewState> = _viewState

    init {
        viewModelScope.launch {
            val theme = when(val getThemeFlowResult = getThemeFlowUseCase()) {
                is GetThemeFlowResult.Success ->
                    getThemeFlowResult.themeFlow.first().toUiTheme()
                is GetThemeFlowResult.Failure ->
                    UiTheme.System
            }
            _viewState.value = SettingsViewState.Loaded.Active(
                selectedTheme = theme,
                notificationState = NotificationState.Disabled,
            )
        }
    }

    fun openThemeSelectionDialog() {
        (_viewState.value as? SettingsViewState.Loaded)?.let {
            _viewState.value = SettingsViewState.Loaded.ThemeSelectionDialog(
                selectedTheme = it.selectedTheme,
                notificationState = it.notificationState,
                pickedTheme = it.selectedTheme,
            )
        }
    }

    fun changeTheme(selectedTheme: UiTheme) {
        // invoke use case to save the theme
        viewModelScope.launch {
            when (savePreferredThemeUseCase(selectedTheme.toDomainTheme())) {
                is SavePreferredThemeResult.Success -> {
                    (_viewState.value as? SettingsViewState.Loaded)?.let {
                        _viewState.value = SettingsViewState.Loaded.Active(
                            selectedTheme = selectedTheme,
                            notificationState = it.notificationState,
                        )
                    }
                }
                is SavePreferredThemeResult.Failure -> {
                    (_viewState.value as? SettingsViewState.Loaded)?.let {
                        _viewState.value = SettingsViewState.Loaded.SavePreferredThemeFailure(
                            selectedTheme = it.selectedTheme,
                            notificationState = it.notificationState,
                        )
                    }
                }
            }
        }
    }

    fun toggleNotifications() {
        // invoke use case

        (_viewState.value as? SettingsViewState.Loaded)?.let {
            val changedNotificationState =
                if (it.notificationState is NotificationState.Disabled) {
                    NotificationState.Enabled(
                        hour = 0,
                        minute = 0,
                    )
                } else {
                    NotificationState.Disabled
                }
            _viewState.value = SettingsViewState.Loaded.Active(
                selectedTheme = it.selectedTheme,
                notificationState = changedNotificationState
            )
        }
    }

    fun logOut() {
        viewModelScope.launch {
            clearTokenUseCase()
        }
    }
}
