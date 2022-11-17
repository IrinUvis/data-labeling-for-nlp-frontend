package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.R
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.components.HorizontalSpacer
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.components.VerticalSpacer
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.extraBigPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.getString
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.mediumPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.smallPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui.components.AnimatedSubmissionError
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui.components.EmailInput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui.components.LogInButton
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui.components.LogInHeadline
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui.components.PasswordInput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui.components.SignUpButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInContent(
    viewState: LogInViewState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLogInClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    start = mediumPadding,
                    top = smallPadding,
                    end = mediumPadding,
                    bottom = mediumPadding,
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LogInHeadline(text = stringResource(R.string.logIn))

            VerticalSpacer(height = extraBigPadding)

            EmailInput(
                text = viewState.credentials.email,
                onTextChanged = onEmailChanged,
                errorMessage = (viewState as? LogInViewState.Active)?.emailInputErrorMessage?.getString(),
                enabled = viewState.inputsEnabled,
            )

            VerticalSpacer(height = mediumPadding)

            PasswordInput(
                text = viewState.credentials.password,
                onTextChanged = onPasswordChanged,
                errorMessage = (viewState as? LogInViewState.Active)?.passwordInputErrorMessage?.getString(),
                enabled = viewState.inputsEnabled,
            )

            AnimatedSubmissionError(viewState)

            VerticalSpacer(height = mediumPadding)
            
            Row {
                SignUpButton(
                    onClick = onSignUpClicked,
                    enabled = viewState.inputsEnabled,
                    isSubmittingSignUp = viewState is LogInViewState.Submitting.SignUp,
                )
                
                HorizontalSpacer(width = mediumPadding)

                LogInButton(
                    onClick = onLogInClicked,
                    enabled = viewState.inputsEnabled,
                    isSubmittingLogIn = viewState is LogInViewState.Submitting.LogIn,
                )
            }

            VerticalSpacer(height = WindowInsets.ime.asPaddingValues().calculateBottomPadding())
        }
    }
}






