package it.nlp.frontend.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()

    SettingsContent(
        viewState = viewState.value,
        onNavigateUp = navigateUp,
        onNavigateToLogin = {
            viewModel.logOut()
            navigateToLogin()
        },
        onThemeButtonClicked = viewModel::openThemeSelectionDialog,
        onThemeSelected = viewModel::changeTheme,
        onPostNotificationsPermissionDenied = viewModel::postNotificationsDenied,
        onNotificationToggled = viewModel::toggleNotifications,
        onReminderTimeSet = viewModel::scheduleReminders,
        onSelectedTimeChanged = viewModel::setTime,
        onGoToSettingsClicked = viewModel::goToSettings,
    )
}
