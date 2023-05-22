package com.example.textparsingapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun TextParsingAppTheme(
    content: @Composable () -> Unit
) {
    ProvideTextParsingAppColors(colors = LightColorsPalette) {

        MaterialTheme(
            typography = TextParsingAppTypography,
            content = content
        )
    }
}