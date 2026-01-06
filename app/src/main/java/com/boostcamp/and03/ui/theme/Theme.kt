package com.boostcamp.and03.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightAnd03Scheme = lightAnd03Colors
private val DarkAnd03Scheme = darkMainColors

@Composable
fun And03Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val mainColorScheme = if(darkTheme) DarkAnd03Scheme else LightAnd03Scheme

    CompositionLocalProvider(
        LocalAnd03Colors provides mainColorScheme,
        LocalAnd03Typography provides and03Typography,
        LocalAnd03Shapes provides and03Shapes
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
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