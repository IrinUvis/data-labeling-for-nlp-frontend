@file:Suppress("MagicNumber")

package it.winter2223.bachelor.ak.frontend.ui.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import it.winter2223.bachelor.ak.frontend.R

private val OpenSans = FontFamily(
    Font(
        resId = R.font.opensans_regular,
    ),
    Font(
        resId = R.font.opensans_italic,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.opensans_light,
        weight = FontWeight.Light,
    ),
    Font(
        resId = R.font.opensans_lightitalic,
        weight = FontWeight.Light,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.opensans_semibold,
        weight = FontWeight.SemiBold,
    ),
    Font(
        resId = R.font.opensans_semibolditalic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.opensans_bold,
        weight = FontWeight.Bold,
    ),
    Font(
        resId = R.font.opensans_bolditalic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.opensans_extrabold,
        weight = FontWeight.ExtraBold,
    ),
    Font(
        resId = R.font.opensans_extrabolditalic,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.opensans_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resId = R.font.opensans_mediumitalic,
        weight = FontWeight.Medium,
        style = FontStyle.Italic,
    ),
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W400,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W400,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W400,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W400,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W400,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W400,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W400,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)
