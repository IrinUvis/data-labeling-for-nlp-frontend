package it.winter2223.bachelor.ak.frontend.ui.settings

import it.winter2223.bachelor.ak.frontend.ui.settings.model.NotificationState
import it.winter2223.bachelor.ak.frontend.ui.settings.model.UiTheme

sealed class SettingsViewState(
    open val selectedTheme: UiTheme?,
    open val notificationState: NotificationState,
) {
    object Loading : SettingsViewState(
        selectedTheme = null,
        notificationState = NotificationState.Disabled,
    )

    sealed class Loaded(
        override val selectedTheme: UiTheme,
        override val notificationState: NotificationState,
    ) : SettingsViewState(
        selectedTheme = selectedTheme,
        notificationState = notificationState,
    ) {
        data class Active(
            override val selectedTheme: UiTheme,
            override val notificationState: NotificationState,
        ): Loaded(
            selectedTheme = selectedTheme,
            notificationState = notificationState,
        )

        data class ThemeSelectionDialog(
            override val selectedTheme: UiTheme,
            override val notificationState: NotificationState,
            val pickedTheme: UiTheme,
        ): Loaded(
            selectedTheme = selectedTheme,
            notificationState = notificationState,
        )
    }
}
