package it.winter2223.bachelor.ak.frontend.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import it.winter2223.bachelor.ak.frontend.BuildConfig
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.GetCommentLabelingReminderStatusResult
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.CancelCommentLabelingRemindersUseCase
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.GetCommentLabelingReminderStatusUseCase
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.ScheduleCommentLabelingRemindersUseCase
import it.winter2223.bachelor.ak.frontend.domain.theme.model.GetThemeFlowResult
import it.winter2223.bachelor.ak.frontend.domain.theme.model.SavePreferredThemeResult
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.GetThemeFlowUseCase
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.SavePreferredThemeUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.ClearTokenUseCase
import it.winter2223.bachelor.ak.frontend.util.reminder.CommentLabelingNotificationHandler.canSendNotifications
import it.winter2223.bachelor.ak.frontend.ui.core.model.UiTheme
import it.winter2223.bachelor.ak.frontend.ui.core.model.toDomainTheme
import it.winter2223.bachelor.ak.frontend.ui.core.model.toUiTheme
import it.winter2223.bachelor.ak.frontend.ui.settings.model.RemindersState
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
    private val getCommentLabelingReminderStatusUseCase: GetCommentLabelingReminderStatusUseCase,
    private val scheduleCommentLabelingRemindersUseCase: ScheduleCommentLabelingRemindersUseCase,
    private val cancelCommentLabelingRemindersUseCase: CancelCommentLabelingRemindersUseCase,
) : ViewModel() {
    companion object {
        private const val STARTING_HOUR_OF_DAY = 20
        private const val STARTING_MINUTE = 0
    }

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

            val notificationsTurnOn =
                when (val reminderStatusResult = getCommentLabelingReminderStatusUseCase()) {
                    is GetCommentLabelingReminderStatusResult.Success -> reminderStatusResult.isScheduled
                    is GetCommentLabelingReminderStatusResult.Failure -> false
                }

            _viewState.value = SettingsViewState.Loaded.Active(
                selectedTheme = theme,
                remindersState = RemindersState(
                    turnOn = notificationsTurnOn,
                ),
            )

        }
    }

    fun openThemeSelectionDialog() {
        (_viewState.value as? SettingsViewState.Loaded)?.let {
            _viewState.value = SettingsViewState.Loaded.ThemeSelectionDialog(
                selectedTheme = it.selectedTheme,
                remindersState = it.remindersState,
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
                            remindersState = it.remindersState,
                        )
                    }
                }
                is SavePreferredThemeResult.Failure -> {
                    (_viewState.value as? SettingsViewState.Loaded)?.let {
                        _viewState.value = SettingsViewState.Loaded.SavePreferredThemeFailure(
                            selectedTheme = it.selectedTheme,
                            remindersState = it.remindersState,
                        )
                    }
                }
            }
        }
    }

    fun toggleNotifications(checked: Boolean) {
        (_viewState.value as? SettingsViewState.Loaded)?.let { state ->
            if (!checked) {
                cancelCommentLabelingRemindersUseCase()
                _viewState.value = SettingsViewState.Loaded.Active(
                    selectedTheme = state.selectedTheme,
                    remindersState = RemindersState(
                        turnOn = false,
                    )
                )
            } else {
                val hasNotificationPermission = canSendNotifications(context)

                if (hasNotificationPermission) {
                    scheduleCommentLabelingRemindersUseCase(STARTING_HOUR_OF_DAY, STARTING_MINUTE)
                    _viewState.value = SettingsViewState.Loaded.Active(
                        selectedTheme = state.selectedTheme,
                        remindersState = RemindersState(
                            turnOn = true,
                        )
                    )
                } else {
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

    fun setTime(hourOfDay: Int, minute: Int) {
        (_viewState.value as? SettingsViewState.Loaded)?.let { state ->
            _viewState.value = SettingsViewState.Loaded.Active(
                selectedTheme = state.selectedTheme,
                remindersState = RemindersState(
                    turnOn = state.remindersState.turnOn,
                    hourOfDay = hourOfDay,
                    minute = minute,
                )
            )
        }
    }

    fun logOut() {
        viewModelScope.launch {
            clearTokenUseCase()
        }
    }
}
