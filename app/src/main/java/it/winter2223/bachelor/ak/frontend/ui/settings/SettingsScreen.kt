package it.winter2223.bachelor.ak.frontend.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()

    SettingsContent(
        viewState = viewState.value,
        onNavigateUp = navigateToHome,
        onNavigateToLogin = {
            viewModel.logOut()
            navigateToLogin()
        },
        onThemeButtonClicked = viewModel::openThemeSelectionDialog,
        onThemeSelected = viewModel::changeTheme,
        onNotificationToggled = viewModel::toggleNotifications
    )
}