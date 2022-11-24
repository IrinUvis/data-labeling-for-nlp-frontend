package it.winter2223.bachelor.ak.frontend.ui.settings

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import it.winter2223.bachelor.ak.frontend.BuildConfig
import it.winter2223.bachelor.ak.frontend.domain.theme.model.GetThemeFlowResult
import it.winter2223.bachelor.ak.frontend.domain.theme.model.SavePreferredThemeResult
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.GetThemeFlowUseCase
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.SavePreferredThemeUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.ClearTokenUseCase
import it.winter2223.bachelor.ak.frontend.ui.core.model.UiTheme
import it.winter2223.bachelor.ak.frontend.ui.core.model.toDomainTheme
import it.winter2223.bachelor.ak.frontend.ui.core.model.toUiTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@SuppressLint("StaticFieldLeak")
@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // should be save according to https://stackoverflow.com/questions/66216839/inject-context-with-hilt-this-field-leaks-a-context-object
    private val clearTokenUseCase: ClearTokenUseCase,
    private val savePreferredThemeUseCase: SavePreferredThemeUseCase,
    private val getThemeFlowUseCase: GetThemeFlowUseCase,
) : ViewModel() {
    private val _viewState: MutableStateFlow<SettingsViewState> =
        MutableStateFlow(SettingsViewState.Loading)
    val viewState: StateFlow<SettingsViewState> = _viewState

    init {
        viewModelScope.launch {
            val theme = when (val getThemeFlowResult = getThemeFlowUseCase()) {
                is GetThemeFlowResult.Success ->
                    getThemeFlowResult.themeFlow.first().toUiTheme()
                is GetThemeFlowResult.Failure ->
                    UiTheme.System
            }
            _viewState.value = SettingsViewState.Loaded.Active(
                selectedTheme = theme,
                notificationsTurnOn = false,
            )
        }
    }

    fun openThemeSelectionDialog() {
        (_viewState.value as? SettingsViewState.Loaded)?.let {
            _viewState.value = SettingsViewState.Loaded.ThemeSelectionDialog(
                selectedTheme = it.selectedTheme,
                notificationsTurnOn = it.notificationsTurnOn,
            )
        }
    }

    fun changeTheme(selectedTheme: UiTheme) {
        viewModelScope.launch {
            when (savePreferredThemeUseCase(selectedTheme.toDomainTheme())) {
                is SavePreferredThemeResult.Success -> {
                    (_viewState.value as? SettingsViewState.Loaded)?.let {
                        _viewState.value = SettingsViewState.Loaded.Active(
                            selectedTheme = selectedTheme,
                            notificationsTurnOn = it.notificationsTurnOn,
                        )
                    }
                }
                is SavePreferredThemeResult.Failure -> {
                    (_viewState.value as? SettingsViewState.Loaded)?.let {
                        _viewState.value = SettingsViewState.Loaded.SavePreferredThemeFailure(
                            selectedTheme = it.selectedTheme,
                            notificationsTurnOn = it.notificationsTurnOn,
                        )
                    }
                }
            }
        }
    }

    fun toggleNotifications(checked: Boolean) {
        // check permissions
        (_viewState.value as? SettingsViewState.Loaded)?.let { state ->
            if (!checked) {
                _viewState.value = SettingsViewState.Loaded.Active(
                    selectedTheme = state.selectedTheme,
                    notificationsTurnOn = false
                )
            } else {
                val hasNotificationPermission = hasPostNotificationsPermission()

                if (hasNotificationPermission) {
                    _viewState.value = SettingsViewState.Loaded.Active(
                        selectedTheme = state.selectedTheme,
                        notificationsTurnOn = true
                    )
                } else {
                    Log.d("NOTIF", "before viewstate change")
                    _viewState.value = SettingsViewState.Loaded.AskForNotificationPermissionDialog(
                        selectedTheme = state.selectedTheme,
                    )
                }
            }
        }
    }

    fun goToSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        intent.data = uri
        context.startActivity(intent)
    }

    fun postNotificationsDenied() {
        (_viewState.value as? SettingsViewState.Loaded)?.let { state ->
            _viewState.value = SettingsViewState.Loaded.AfterPermissionDeniedDialog(
                selectedTheme = state.selectedTheme,
            )
        }
    }

    private fun hasPostNotificationsPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    fun logOut() {
        viewModelScope.launch {
            clearTokenUseCase()
        }
    }
}
