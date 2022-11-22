package it.winter2223.bachelor.ak.frontend.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.ui.core.component.HorizontalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.component.VerticalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.extraBigPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.mediumPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.smallPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.getString
import it.winter2223.bachelor.ak.frontend.ui.login.component.AnimatedSubmissionError
import it.winter2223.bachelor.ak.frontend.ui.login.component.EmailInput
import it.winter2223.bachelor.ak.frontend.ui.login.component.LogInButton
import it.winter2223.bachelor.ak.frontend.ui.login.component.LogInHeadline
import it.winter2223.bachelor.ak.frontend.ui.login.component.PasswordInput
import it.winter2223.bachelor.ak.frontend.ui.login.component.SignUpButton

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
            val context = LocalContext.current
            LogInHeadline(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.logIn),
            )

            VerticalSpacer(height = extraBigPadding)

            EmailInput(
                text = viewState.credentials.email,
                onTextChanged = onEmailChanged,
                errorMessage = (viewState as? LogInViewState.Active)?.emailInputErrorMessage?.getString(context),
                enabled = viewState.inputsEnabled,
            )

            VerticalSpacer(height = mediumPadding)

            PasswordInput(
                text = viewState.credentials.password,
                onTextChanged = onPasswordChanged,
                errorMessage = (viewState as? LogInViewState.Active)?.passwordInputErrorMessage?.getString(context),
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






