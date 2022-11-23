package it.winter2223.bachelor.ak.frontend.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.ClearTokenUseCase
import it.winter2223.bachelor.ak.frontend.ui.settings.model.NotificationState
import it.winter2223.bachelor.ak.frontend.ui.settings.model.UiTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val clearTokenUseCase: ClearTokenUseCase,
) : ViewModel() {
    private val _viewState: MutableStateFlow<SettingsViewState> =
        MutableStateFlow(SettingsViewState.Loading)
    val viewState: StateFlow<SettingsViewState> = _viewState

    init {
        viewModelScope.launch {
            delay(200)
            _viewState.value = SettingsViewState.Loaded.Active(
                selectedTheme = UiTheme.Light,
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

        (_viewState.value as? SettingsViewState.Loaded)?.let {
            _viewState.value = SettingsViewState.Loaded.Active(
                selectedTheme = selectedTheme,
                notificationState = it.notificationState,
            )
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