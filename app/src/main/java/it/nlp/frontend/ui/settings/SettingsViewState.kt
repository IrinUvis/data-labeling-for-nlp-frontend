package it.nlp.frontend.ui.settings

import it.nlp.frontend.ui.core.model.UiTheme
import it.nlp.frontend.ui.settings.model.RemindersState
import it.nlp.frontend.ui.settings.model.UiReminderTime

sealed class SettingsViewState(
    open val selectedTheme: UiTheme?,
    open val remindersState: RemindersState,
) {
    object Loading : SettingsViewState(
        selectedTheme = null,
        remindersState = RemindersState(
            turnOn = false,
            scheduledReminderTime = UiReminderTime(),
            selectedReminderTime = UiReminderTime(),
        ),
    )

    sealed class Loaded(
        override val selectedTheme: UiTheme,
        override val remindersState: RemindersState,
    ) : SettingsViewState(
        selectedTheme = selectedTheme,
        remindersState = remindersState
    ) {
        data class Active(
            override val selectedTheme: UiTheme,
            override val remindersState: RemindersState,
        ) : Loaded(
            selectedTheme = selectedTheme,
            remindersState = remindersState,
        )

        data class ThemeSelectionDialog(
            override val selectedTheme: UiTheme,
            override val remindersState: RemindersState,
        ) : Loaded(
            selectedTheme = selectedTheme,
            remindersState = remindersState,
        )

        data class AskForNotificationPermissionDialog(
            override val selectedTheme: UiTheme,
            val reminderTimes: UiReminderTime,
            ) : Loaded(
            selectedTheme = selectedTheme,
            remindersState = RemindersState(
                turnOn = false,
                scheduledReminderTime = reminderTimes,
                selectedReminderTime = reminderTimes,
            ),
        )

        data class AfterPermissionDeniedDialog(
            override val selectedTheme: UiTheme,
            val reminderTimes: UiReminderTime,
        ) : Loaded(
            selectedTheme = selectedTheme,
            remindersState = RemindersState(
                turnOn = false,
                scheduledReminderTime = reminderTimes,
                selectedReminderTime = reminderTimes,
            ),
        )

        data class SavePreferredThemeFailure(
            override val selectedTheme: UiTheme,
            override val remindersState: RemindersState,
        ) : Loaded(
            selectedTheme = selectedTheme,
            remindersState = remindersState,
        )
    }
}
