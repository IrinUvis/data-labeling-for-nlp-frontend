package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.R
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.UiText
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.Credentials
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.LogInException
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.Token
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.UserOutput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository.LoginRepository
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository.TokenRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository,
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
            credentials = currentCredentials.withUpdatedEmail(newEmail),
            emailInputErrorMessage = null,
            passwordInputErrorMessage = currentPasswordErrorMessage,
        )
    }

    fun onPasswordChanged(newPassword: String) {
        val currentCredentials = _viewState.value.credentials
        val currentEmailErrorMessage =
            (_viewState.value as? LogInViewState.Active)?.emailInputErrorMessage

        _viewState.value = LogInViewState.Active(
            credentials = currentCredentials.withUpdatedPassword(newPassword),
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
            val logInResult = loginRepository.logIn(currentCredentials)
            handleLoginResult(
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
            val signUpResult = loginRepository.signUp(currentCredentials)
            handleLoginResult(
                logInCredentials = currentCredentials,
                result = signUpResult,
            )
        }
    }

    private suspend fun handleLoginResult(
        logInCredentials: Credentials,
        result: Result<UserOutput>,
    ) = coroutineScope {
        result.fold(
            onSuccess = {
                tokenRepository.storeToken(
                    Token(
                        authToken = it.idToken,
                        refreshToken = it.refreshToken,
                    )
                )
                val token = tokenRepository.tokenFlow().first().toString()
                Log.d(TAG, token)
                _viewState.value = LogInViewState.Completed
            },
            onFailure = { exception ->
                when (exception) {
                    is LogInException.InvalidCredentialsException -> {
                        _viewState.value = LogInViewState.SubmissionError(
                            credentials = logInCredentials,
                            errorMessage = UiText.ResourceText(R.string.invalidCredentials)
                        )
                    }
                    is LogInException.EmptyCredentialsException -> {
                        _viewState.value = LogInViewState.Active(
                            credentials = logInCredentials,
                            emailInputErrorMessage = UiText.ResourceText(R.string.emptyEmailInputErrorMessage),
                            passwordInputErrorMessage = UiText.ResourceText(R.string.emptyPasswordInputErrorMessage),
                        )
                    }
                }
            }
        )
    }

}

private fun Credentials.withUpdatedEmail(newEmail: String) =
    Credentials(email = newEmail, password = password)

private fun Credentials.withUpdatedPassword(newPassword: String) =
    Credentials(email = email, password = newPassword)
