package it.nlp.frontend.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import it.nlp.frontend.BuildConfig
import it.nlp.frontend.domain.reminder.model.GetTextsLabelingReminderStatusResult
import it.nlp.frontend.domain.reminder.model.GetReminderTimeFlowResult
import it.nlp.frontend.domain.reminder.model.ReminderTime
import it.nlp.frontend.domain.reminder.model.StoreReminderTimeResult
import it.nlp.frontend.domain.reminder.usecase.CancelTextsLabelingRemindersUseCase
import it.nlp.frontend.domain.reminder.usecase.GetTextsLabelingReminderStatusUseCase
import it.nlp.frontend.domain.reminder.usecase.GetReminderTimeFlowUseCase
import it.nlp.frontend.domain.reminder.usecase.ScheduleTextsLabelingRemindersUseCase
import it.nlp.frontend.domain.reminder.usecase.StoreReminderTimeUseCase
import it.nlp.frontend.domain.theme.model.GetThemeFlowResult
import it.nlp.frontend.domain.theme.model.SavePreferredThemeResult
import it.nlp.frontend.domain.theme.usecase.GetThemeFlowUseCase
import it.nlp.frontend.domain.theme.usecase.SavePreferredThemeUseCase
import it.nlp.frontend.domain.token.usecase.ClearTokenUseCase
import it.nlp.frontend.util.reminder.TextsLabelingNotificationHandler.canSendNotifications
import it.nlp.frontend.ui.core.model.UiTheme
import it.nlp.frontend.ui.core.model.toDomainTheme
import it.nlp.frontend.ui.core.model.toUiTheme
import it.nlp.frontend.ui.settings.model.RemindersState
import it.nlp.frontend.ui.settings.model.UiReminderTime
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


@SuppressLint("StaticFieldLeak")
@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // should be save according to https://stackoverflow.com/questions/66216839/inject-context-with-hilt-this-field-leaks-a-context-object
    private val clearTokenUseCase: ClearTokenUseCase,
    private val savePreferredThemeUseCase: SavePreferredThemeUseCase,
    private val getThemeFlowUseCase: GetThemeFlowUseCase,
    private val getTextsLabelingReminderStatusUseCase: GetTextsLabelingReminderStatusUseCase,
    private val scheduleTextsLabelingRemindersUseCase: ScheduleTextsLabelingRemindersUseCase,
    private val cancelTextsLabelingRemindersUseCase: CancelTextsLabelingRemindersUseCase,
    private val storeReminderTimeUseCase: StoreReminderTimeUseCase,
    private val getReminderTimeFlowUseCase: GetReminderTimeFlowUseCase,
) : ViewModel() {
    private val _viewState: MutableStateFlow<SettingsViewState> =
        MutableStateFlow(SettingsViewState.Loading)
    val viewState: StateFlow<SettingsViewState> = _viewState

    init {
        viewModelScope.launch {
            val theme = async {
                when (val getThemeFlowResult = getThemeFlowUseCase()) {
                    is GetThemeFlowResult.Success ->
                        getThemeFlowResult.themeFlow.first().toUiTheme()
                    is GetThemeFlowResult.Failure ->
                        UiTheme.System
                }
            }

            val reminderTime = async {
                when (val getReminderTimeFlowResult = getReminderTimeFlowUseCase()) {
                    is GetReminderTimeFlowResult.Success -> {
                        getReminderTimeFlowResult.reminderTimeFlow.first()?.toUiReminderTime()
                            ?: UiReminderTime()
                    }
                    is GetReminderTimeFlowResult.Failure -> UiReminderTime()
                }
            }

            val notificationsTurnOn =
                when (val reminderStatusResult = getTextsLabelingReminderStatusUseCase()) {
                    is GetTextsLabelingReminderStatusResult.Success -> reminderStatusResult.isScheduled
                }

            _viewState.value = SettingsViewState.Loaded.Active(
                selectedTheme = theme.await(),
                remindersState = RemindersState(
                    turnOn = notificationsTurnOn,
                    scheduledReminderTime = reminderTime.await(),
                    selectedReminderTime = reminderTime.await(),
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
                cancelTextsLabelingRemindersUseCase()
                _viewState.value = SettingsViewState.Loaded.Active(
                    selectedTheme = state.selectedTheme,
                    remindersState = RemindersState(
                        turnOn = false,
                        scheduledReminderTime = state.remindersState.scheduledReminderTime,
                        selectedReminderTime = state.remindersState.scheduledReminderTime,
                    )
                )
            } else {
                val hasNotificationPermission = canSendNotifications(context)

                if (hasNotificationPermission) {
                    scheduleReminders()
                } else {
                    _viewState.value = SettingsViewState.Loaded.AskForNotificationPermissionDialog(
                        selectedTheme = state.selectedTheme,
                        reminderTimes = state.remindersState.scheduledReminderTime,
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
                reminderTimes = state.remindersState.scheduledReminderTime,
            )
        }
    }

    fun scheduleReminders() {
        (_viewState.value as? SettingsViewState.Loaded)?.let { state ->
            val now = Calendar.getInstance().apply {
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            scheduleTextsLabelingRemindersUseCase(
                reminderTime = state.remindersState.selectedReminderTime.toDomainReminderTime(),
                now = now,
            )
            viewModelScope.launch {
                when (storeReminderTimeUseCase(state.remindersState.selectedReminderTime.toDomainReminderTime())) {
                    is StoreReminderTimeResult.Success -> {
                        _viewState.value = SettingsViewState.Loaded.Active(
                            selectedTheme = state.selectedTheme,
                            remindersState = RemindersState(
                                turnOn = true,
                                scheduledReminderTime = state.remindersState.selectedReminderTime,
                                selectedReminderTime = state.remindersState.selectedReminderTime
                            )
                        )
                    }
                    is StoreReminderTimeResult.Failure -> {
                        _viewState.value = SettingsViewState.Loaded.Active(
                            selectedTheme = state.selectedTheme,
                            remindersState = RemindersState(
                                turnOn = false,
                                scheduledReminderTime = state.remindersState.scheduledReminderTime,
                                selectedReminderTime = state.remindersState.selectedReminderTime
                            )
                        )
                    }
                }
            }
        }
    }

    fun setTime(reminderTime: UiReminderTime) {
        (_viewState.value as? SettingsViewState.Loaded)?.let { state ->
            _viewState.value = SettingsViewState.Loaded.Active(
                selectedTheme = state.selectedTheme,
                remindersState = RemindersState(
                    turnOn = state.remindersState.turnOn,
                    scheduledReminderTime = state.remindersState.scheduledReminderTime,
                    selectedReminderTime = reminderTime,
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

fun ReminderTime.toUiReminderTime() = UiReminderTime(hour, minute)

fun UiReminderTime.toDomainReminderTime() = ReminderTime(hourOfDay, minute)
