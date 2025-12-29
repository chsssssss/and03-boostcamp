package com.boostcamp.and03.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightMainScheme = lightMainColors
private val DarkMainScheme = darkMainColors

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val mainColorScheme = if(darkTheme) DarkMainScheme else LightMainScheme

    CompositionLocalProvider(
        LocalMainColors provides mainColorScheme,
        LocalMainTypography provides mainTypography,
        LocalMainShapes provides mainShapes
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

object MainTheme {
    val colors: MainColors
        @Composable
        get() = LocalMainColors.current

    val typography: MainTypography
        @Composable
        get() = LocalMainTypography.current

    val shapes: MainShapes
        @Composable
        get() = LocalMainShapes.current
}