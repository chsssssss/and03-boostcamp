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
    val and03Colors = if(darkTheme) darkMainColors else lightMainColors

    val mainColorScheme = lightColorScheme(
        primary = and03Colors.primary,
        onPrimary = and03Colors.onPrimary,
        primaryContainer = and03Colors.primaryContainer,
        onPrimaryContainer = and03Colors.onPrimaryContainer,
        secondary = and03Colors.secondary,
        onSecondary = and03Colors.onSecondary,
        secondaryContainer = and03Colors.secondaryContainer,
        onSecondaryContainer = and03Colors.onSecondaryContainer,
        background = and03Colors.background,
        onBackground = and03Colors.onBackground,
        surface = and03Colors.surface,
        onSurface = and03Colors.onSurface,
        surfaceVariant = and03Colors.surfaceVariant,
        onSurfaceVariant = and03Colors.onSurfaceVariant,
        error = and03Colors.error,
        onError = and03Colors.onError,
        outline = and03Colors.outline,
        outlineVariant = and03Colors.outlineVariant,
        scrim = and03Colors.scrim
    )

    CompositionLocalProvider(
        LocalAnd03Colors provides and03Colors,
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