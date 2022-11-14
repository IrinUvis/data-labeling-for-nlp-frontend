package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

sealed class UiText {
    data class StringText(val value: String) : UiText()

    data class ResourceText(@StringRes val value: Int) : UiText()
}

fun UiText.getString(context: Context): String {
    return when (this) {
        is UiText.StringText -> this.value
        is UiText.ResourceText -> context.getString(this.value)
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun UiText.getString() = getString(LocalContext.current)
