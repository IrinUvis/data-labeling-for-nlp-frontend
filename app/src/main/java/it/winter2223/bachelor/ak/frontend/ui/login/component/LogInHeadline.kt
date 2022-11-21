package it.winter2223.bachelor.ak.frontend.ui.login.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LogInHeadline(text: String) {
    Text(text = text, style = MaterialTheme.typography.headlineLarge)
}
