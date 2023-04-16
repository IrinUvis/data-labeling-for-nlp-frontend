package it.nlp.frontend.ui.core.model

import androidx.annotation.StringRes
import it.nlp.frontend.R
import it.nlp.frontend.domain.theme.model.Theme

enum class UiTheme(@StringRes val resId: Int) {
    Light(R.string.lightThemeEnumText),
    Dark(R.string.darkThemeEnumText),
    System(R.string.systemDefaultThemeEnumText)
}

fun UiTheme.toDomainTheme() = Theme.valueOf(name)

fun Theme.toUiTheme() = UiTheme.valueOf(name)
