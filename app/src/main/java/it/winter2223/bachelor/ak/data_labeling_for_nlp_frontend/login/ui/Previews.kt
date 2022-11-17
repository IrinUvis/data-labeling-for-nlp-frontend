package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.UiText
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.components.PreviewThemeWithBackground
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui.model.UiCredentials

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
