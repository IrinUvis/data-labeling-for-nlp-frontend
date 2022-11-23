package it.winter2223.bachelor.ak.frontend.ui.settings.model

import androidx.annotation.StringRes
import it.winter2223.bachelor.ak.frontend.R

enum class UiTheme(@StringRes val resId: Int) {
    Light(R.string.lightThemeEnumText),
    Dark(R.string.darkThemeEnumText),
    System(R.string.systemDefaultThemeEnumText)
}
