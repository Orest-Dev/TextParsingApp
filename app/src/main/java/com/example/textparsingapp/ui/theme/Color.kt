package com.example.textparsingapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val GrayPrimary = Color(0xFF575757)
private val GraySecondary = Color(0xFF949494)
private val Background = Color(0xFFFCFCFC)
private val Green = Color(0xFF55C595)
private val DarkGreen = Color(0xFF289460)

val LightColorsPalette = TextParsingAppColors(
    text = GraySecondary,
    textDark = GrayPrimary,
    textLight = Color.White,
    textGreenDark = DarkGreen,
    buttonSelected = Green,
    buttonUnselected = GraySecondary,
    background = Background,
    chipsBackground = Color.White,
    snackBarBackground = GrayPrimary
)

@Stable
data class TextParsingAppColors(
    val text: Color,
    val textDark: Color,
    val textLight: Color,
    val textGreenDark: Color,

    val buttonSelected: Color,
    val buttonUnselected: Color,

    val background: Color,
    val chipsBackground: Color,
    val snackBarBackground: Color
)

internal val LocalTextParsingAppColors = staticCompositionLocalOf<TextParsingAppColors> {
    error("No colors provided")
}

@Composable
internal fun ProvideTextParsingAppColors(
    colors: TextParsingAppColors,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalTextParsingAppColors provides colors, content = content)
}