package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.R
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.components.AppTextField
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.components.HorizontalSpacer
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.components.VerticalSpacer
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.extraBigPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.getString
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.mediumPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.smallPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    viewState: LoginViewState,
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
                errorMessage = (viewState as? LoginViewState.Active)?.emailInputErrorMessage?.getString(),
                enabled = viewState.inputsEnabled,
            )

            VerticalSpacer(height = mediumPadding)

            PasswordInput(
                text = viewState.credentials.password,
                onTextChanged = onPasswordChanged,
                errorMessage = (viewState as? LoginViewState.Active)?.passwordInputErrorMessage?.getString(),
                enabled = viewState.inputsEnabled,
            )

            AnimatedSubmissionError(viewState)

            VerticalSpacer(height = mediumPadding)

            LogInButton(
                onClick = onLogInClicked,
                enabled = viewState.inputsEnabled,
                isSubmittingLogIn = viewState is LoginViewState.Submitting.LogIn,
            )

            VerticalSpacer(height = smallPadding)

            SignUpButton(
                onClick = onSignUpClicked,
                enabled = viewState.inputsEnabled,
                isSubmittingSignUp = viewState is LoginViewState.Submitting.SignUp,
            )

            VerticalSpacer(height = WindowInsets.ime.asPaddingValues().calculateBottomPadding())
        }
    }
}

@Composable
private fun LogInHeadline(text: String) {
    Text(text = text, style = MaterialTheme.typography.headlineLarge)
}

@Composable
private fun EmailInput(
    text: String,
    onTextChanged: (String) -> Unit,
    errorMessage: String?,
    enabled: Boolean,
) {
    AppTextField(
        text = text,
        onTextChanged = onTextChanged,
        labelText = stringResource(R.string.email),
        errorMessage = errorMessage,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier.padding(start = mediumPadding),
                imageVector = Icons.Default.Email,
                contentDescription = stringResource(R.string.emailIconContentDescription),
            )
        },
        trailingIcon = {
            IconButton(onClick = { onTextChanged("") }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.clearIconContentDescription),
                )
            }
        }
    )
}

@Composable
private fun PasswordInput(
    text: String,
    onTextChanged: (String) -> Unit,
    errorMessage: String?,
    enabled: Boolean,
) {
    val focusManager = LocalFocusManager.current
    AppTextField(
        text = text,
        onTextChanged = onTextChanged,
        labelText = stringResource(R.string.password),
        errorMessage = errorMessage,
        enabled = enabled,
        visualTransformation = PasswordVisualTransformation('*'),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier.padding(start = mediumPadding),
                imageVector = Icons.Default.Lock,
                contentDescription = stringResource(R.string.lockIconContentDescription),
            )
        },
        trailingIcon = {
            IconButton(onClick = { onTextChanged("") }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.clearIconContentDescription),
                )
            }
        }
    )
}

@Composable
private fun AnimatedSubmissionError(viewState: LoginViewState) {
    AnimatedVisibility(visible = viewState is LoginViewState.SubmissionError) {
        val context = LocalContext.current
        val errorMessage by remember {
            mutableStateOf((viewState as? LoginViewState.SubmissionError)?.errorMessage?.getString(
                context))
        }
        errorMessage?.let {
            Text(                modifier = Modifier
                .padding(top = 12.dp),
                text = it,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}

@Composable
private fun LogInButton(
    onClick: () -> Unit,
    enabled: Boolean,
    isSubmittingLogIn: Boolean,
) {
    Button(modifier = Modifier.animateContentSize(), onClick = onClick, enabled = enabled) {
        AnimatedVisibility(visible = isSubmittingLogIn) {
            Row {
                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                HorizontalSpacer(width = mediumPadding)
            }
        }
        Text(text = stringResource(R.string.logIn))
    }
}

@Composable
private fun SignUpButton(
    onClick: () -> Unit,
    enabled: Boolean,
    isSubmittingSignUp: Boolean,
) {
    OutlinedButton(modifier = Modifier.animateContentSize(), onClick = onClick, enabled = enabled) {
        AnimatedVisibility(visible = isSubmittingSignUp) {
            Row {
                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                HorizontalSpacer(width = mediumPadding)
            }
        }
        Text(text = stringResource(R.string.signUp))
    }
}
