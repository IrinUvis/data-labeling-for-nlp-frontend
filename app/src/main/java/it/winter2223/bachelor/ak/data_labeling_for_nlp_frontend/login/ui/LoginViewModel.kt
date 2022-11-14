package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.UiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    companion object {
        const val TAG = "LoginVM"
    }

    private val _viewState: MutableStateFlow<LoginViewState> = MutableStateFlow(
        value = LoginViewState.Initial
    )
    val viewState: StateFlow<LoginViewState> = _viewState

    fun onEmailChanged(newEmail: String) {
        val currentCredentials = _viewState.value.credentials
        val currentPasswordErrorMessage =
            (_viewState.value as? LoginViewState.Active)?.passwordInputErrorMessage

        _viewState.value = LoginViewState.Active(
            credentials = currentCredentials.withUpdatedEmail(newEmail),
            emailInputErrorMessage = null,
            passwordInputErrorMessage = currentPasswordErrorMessage,
        )
    }

    fun onPasswordChanged(newPassword: String) {
        val currentCredentials = _viewState.value.credentials
        val currentEmailErrorMessage =
            (_viewState.value as? LoginViewState.Active)?.emailInputErrorMessage

        _viewState.value = LoginViewState.Active(
            credentials = currentCredentials.withUpdatedPassword(newPassword),
            emailInputErrorMessage = currentEmailErrorMessage,
            passwordInputErrorMessage = null,
        )
    }

    fun onLogInPressed() {
        val currentCredentials = _viewState.value.credentials

        _viewState.value = LoginViewState.Submitting.LogIn(
            credentials = currentCredentials,
        )

        viewModelScope.launch {
            @Suppress("MagicNumber")
            delay(2000)
            _viewState.value = LoginViewState.Completed
        }
    }

    fun onSignUpPressed() {
        val currentCredentials = _viewState.value.credentials

        _viewState.value = LoginViewState.Submitting.SignUp(
            credentials = currentCredentials,
        )

        viewModelScope.launch {
            @Suppress("MagicNumber")
            delay(2000)
            _viewState.value = LoginViewState.SubmissionError(
                credentials = currentCredentials,
                errorMessage = UiText.StringText("error")
            )
        }
    }
}
