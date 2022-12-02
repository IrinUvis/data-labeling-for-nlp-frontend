package it.winter2223.bachelor.ak.frontend.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.Credentials
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.LogInResult
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.AuthenticationActivity
import it.winter2223.bachelor.ak.frontend.domain.authentication.usecase.CredentialsLogInOrSignUpUseCase
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.UiText
import it.winter2223.bachelor.ak.frontend.ui.login.model.UiCredentials
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val credentialsLogInOrSignUpUseCase: CredentialsLogInOrSignUpUseCase,
) : ViewModel() {
    companion object {
        const val TAG = "LoginVM"
    }

    private val _viewState: MutableStateFlow<LogInViewState> = MutableStateFlow(
        value = LogInViewState.Initial
    )
    val viewState: StateFlow<LogInViewState> = _viewState

    fun onEmailChanged(newEmail: String) {
        val currentCredentials = _viewState.value.credentials
        val currentPasswordErrorMessage =
            (_viewState.value as? LogInViewState.Active)?.passwordInputErrorMessage

        _viewState.value = LogInViewState.Active(
            credentials = currentCredentials.withUpdatedEmail(newEmail.trim()),
            emailInputErrorMessage = null,
            passwordInputErrorMessage = currentPasswordErrorMessage,
        )
    }

    fun onPasswordChanged(newPassword: String) {
        val currentCredentials = _viewState.value.credentials
        val currentEmailErrorMessage =
            (_viewState.value as? LogInViewState.Active)?.emailInputErrorMessage

        _viewState.value = LogInViewState.Active(
            credentials = currentCredentials.withUpdatedPassword(newPassword.trim()),
            emailInputErrorMessage = currentEmailErrorMessage,
            passwordInputErrorMessage = null,
        )
    }

    fun onLogInPressed() {
        val currentCredentials = _viewState.value.credentials

        _viewState.value = LogInViewState.Submitting.LogIn(
            credentials = currentCredentials,
        )

        viewModelScope.launch {
            val logInResult =
                credentialsLogInOrSignUpUseCase(
                    credentials = currentCredentials.toDomainCredentials(),
                    authenticationActivity = AuthenticationActivity.LogIn,
                )
            handleLogInResult(
                logInCredentials = currentCredentials,
                result = logInResult,
            )
        }
    }

    fun onSignUpPressed() {
        val currentCredentials = _viewState.value.credentials

        _viewState.value = LogInViewState.Submitting.SignUp(
            credentials = currentCredentials,
        )

        viewModelScope.launch {
            val logInResult = credentialsLogInOrSignUpUseCase(
                credentials = currentCredentials.toDomainCredentials(),
                authenticationActivity = AuthenticationActivity.SignUp,
            )
            handleLogInResult(
                logInCredentials = currentCredentials,
                result = logInResult,
            )
        }
    }

    private fun handleLogInResult(
        logInCredentials: UiCredentials,
        result: LogInResult,
    ) {
        _viewState.value = when (result) {
            is LogInResult.Success -> LogInViewState.Completed
            is LogInResult.Failure.Network -> LogInViewState.SubmissionError(
                credentials = logInCredentials,
                errorMessage = UiText.ResourceText(R.string.networkErrorMessage)
            )
            is LogInResult.Failure.InvalidCredentials -> {
                var emailInputErrorMessage: UiText? = null
                var passwordInputErrorMessage: UiText? = null
                if (result.emptyEmail) {
                    emailInputErrorMessage =
                        UiText.ResourceText(R.string.emptyEmailInputErrorMessage)
                } else if (result.badEmailFormat) {
                    emailInputErrorMessage = UiText.ResourceText(R.string.incorrectEmailFormatErrorMessage)
                }
                if (result.emptyPassword) {
                    passwordInputErrorMessage =
                        UiText.ResourceText(R.string.emptyPasswordInputErrorMessage)

                } else if (result.passwordLessThanSixCharacters) {
                    passwordInputErrorMessage =
                        UiText.ResourceText(R.string.passwordCannotBeLessThanSixCharactersErrorMessage)
                }
                LogInViewState.Active(
                    credentials = logInCredentials,
                    emailInputErrorMessage = emailInputErrorMessage,
                    passwordInputErrorMessage = passwordInputErrorMessage,
                )
            }
            is LogInResult.Failure.WrongCredentials -> LogInViewState.SubmissionError(
                credentials = logInCredentials,
                errorMessage = UiText.ResourceText(R.string.wrongCredentialsErrorMessage)
            )
            is LogInResult.Failure.DataStore -> LogInViewState.SubmissionError(
                credentials = logInCredentials,
                errorMessage = UiText.ResourceText(R.string.unexpectedErrorOccurredErrorMessage)
            )
            is LogInResult.Failure.Unknown -> LogInViewState.SubmissionError(
                credentials = logInCredentials,
                errorMessage = UiText.ResourceText(R.string.unknownErrorOccurredErrorMessage)
            )
        }
    }
}

private fun UiCredentials.toDomainCredentials() = Credentials(email, password)
