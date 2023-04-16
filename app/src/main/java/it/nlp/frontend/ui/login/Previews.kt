package it.nlp.frontend.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import it.nlp.frontend.ui.core.component.PreviewThemeWithBackground
import it.nlp.frontend.ui.core.helpers.UiText
import it.nlp.frontend.ui.login.model.UiCredentials

@Preview
@Composable
fun DarkLogInContentPreview(
    @PreviewParameter(LogInContentPreviewParameterProvider::class) viewState: LogInViewState,
) {
    PreviewThemeWithBackground(darkTheme = true) {
        LogInContent(
            viewState = viewState,
            onEmailChanged = { },
            onPasswordChanged = { },
            onLogInClicked = { },
            onSignUpClicked = { },
        )
    }
}

@Preview
@Composable
fun LightLogInContentPreview(
    @PreviewParameter(LogInContentPreviewParameterProvider::class) viewState: LogInViewState,
) {
    PreviewThemeWithBackground(darkTheme = false) {
        LogInContent(
            viewState = viewState,
            onEmailChanged = { },
            onPasswordChanged = { },
            onLogInClicked = { },
            onSignUpClicked = { },
        )
    }
}

class LogInContentPreviewParameterProvider : PreviewParameterProvider<LogInViewState> {
    override val values: Sequence<LogInViewState>
        get() {
            val previewEmail = "test@preview.com"
            val previewPassword = "123456"
            val errorMessage = "An error has occurred"

            return sequenceOf(
                LogInViewState.Initial,
                LogInViewState.Active(
                    credentials = UiCredentials(
                        email = previewEmail,
                        password = previewPassword,
                    ),
                ),
                LogInViewState.Active(
                    credentials = UiCredentials(
                        email = previewEmail,
                        password = previewPassword,
                    ),
                    emailInputErrorMessage = UiText.StringText(errorMessage),
                    passwordInputErrorMessage = UiText.StringText(errorMessage),
                ),
                LogInViewState.Submitting.LogIn(
                    credentials = UiCredentials(
                        email = previewEmail,
                        password = previewPassword,
                    ),
                ),
                LogInViewState.Submitting.SignUp(
                    credentials = UiCredentials(
                        email = previewEmail,
                        password = previewPassword,
                    ),
                ),
                LogInViewState.SubmissionError(
                    credentials = UiCredentials(
                        email = previewEmail,
                        password = previewPassword,
                    ),
                    errorMessage = UiText.StringText(errorMessage)
                ),
                LogInViewState.Completed
            )
        }
}
