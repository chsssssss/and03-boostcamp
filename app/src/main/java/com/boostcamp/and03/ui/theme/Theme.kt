package com.boostcamp.and03.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun And03Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val mainColors = if(darkTheme) darkMainColors else lightMainColors

    val mainColorScheme = lightColorScheme(
        primary = mainColors.primary,
        onPrimary = mainColors.onPrimary,
        primaryContainer = mainColors.primaryContainer,
        onPrimaryContainer = mainColors.onPrimaryContainer,
        secondary = mainColors.secondary,
        onSecondary = mainColors.onSecondary,
        secondaryContainer = mainColors.secondaryContainer,
        onSecondaryContainer = mainColors.onSecondaryContainer,
        background = mainColors.background,
        onBackground = mainColors.onBackground,
        surface = mainColors.surface,
        onSurface = mainColors.onSurface,
        surfaceVariant = mainColors.surfaceVariant,
        onSurfaceVariant = mainColors.onSurfaceVariant,
        error = mainColors.error,
        onError = mainColors.onError,
        outline = mainColors.outline,
        outlineVariant = mainColors.outlineVariant,
        scrim = mainColors.scrim
    )

    CompositionLocalProvider(
        LocalAnd03Colors provides mainColors,
        LocalAnd03Typography provides and03Typography,
        LocalAnd03Shapes provides and03Shapes
    ) {
        MaterialTheme(
            colorScheme = mainColorScheme,
            typography = Typography,
            content = content
        )
    }
}

object And03Theme {
    val colors: And03Colors
        @Composable
        get() = LocalAnd03Colors.current

    val typography: And03Typography
        @Composable
        get() = LocalAnd03Typography.current

    val shapes: And03Shapes
        @Composable
        get() = LocalAnd03Shapes.current
}