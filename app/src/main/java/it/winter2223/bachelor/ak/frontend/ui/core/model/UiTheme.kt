package it.winter2223.bachelor.ak.frontend.ui.core.model

import androidx.annotation.StringRes
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.domain.theme.model.Theme

enum class UiTheme(@StringRes val resId: Int) {
    Light(R.string.lightThemeEnumText),
    Dark(R.string.darkThemeEnumText),
    System(R.string.systemDefaultThemeEnumText)
}

fun UiTheme.toDomainTheme() = Theme.valueOf(name)

fun Theme.toUiTheme() = UiTheme.valueOf(name)
