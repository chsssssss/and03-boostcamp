package com.boostcamp.and03.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFF2563EB)
val OnPrimary = Color(0xFFFFFFFF)

val PrimaryContainer = Color(0xFFEFF3FF)
val OnPrimaryContainer = Color(0xFF1E3A8A)

val Background = Color(0xFFFFFFFF)
val OnBackground = Color(0xFF000000)

val Surface = Color(0xFFFFFFFF)
val OnSurface = Color(0xFF000000)

val SurfaceVariant = Color(0xFFF3F4F6)
val OnSurfaceVariant = Color(0xFF6C7280)

val Outline = Color(0xFFE6E7EB)
val OutlineVariant = Color(0xFF9DA3AF)

val Secondary = Color(0xFF6C7280)
val OnSecondary = Color(0xFFFFFFFF)

val SecondaryContainer = Color(0xFFF3F4F6)
val OnSecondaryContainer = Color(0xFF6C7280)

val Error = Color(0xFFDC2626)
val OnError = Color(0xFFFFFFFF)

val Scrim = Color(0x99000000)

@Immutable
data class MainColors(
    val primary: Color,
    val onPrimary: Color,

    val primaryContainer: Color,
    val onPrimaryContainer: Color,

    val background: Color,
    val onBackground: Color,

    val surface: Color,
    val onSurface: Color,

    val surfaceVariant: Color,
    val onSurfaceVariant: Color,

    val outline: Color,
    val outlineVariant: Color,

    val secondary: Color,
    val onSecondary: Color,

    val secondaryContainer: Color,
    val onSecondaryContainer: Color,

    val error: Color,
    val onError: Color,

    val scrim: Color
)

internal val lightMainColors = MainColors(
    primary = Primary,
    onPrimary = OnPrimary,

    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,

    background = Background,
    onBackground = OnBackground,

    surface = Surface,
    onSurface = OnSurface,

    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    outline = Outline,
    outlineVariant = OutlineVariant,

    secondary = Secondary,
    onSecondary = OnSecondary,

    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,

    error = Error,
    onError = OnError,

    scrim = Scrim
)

internal val darkMainColors = MainColors(
    primary = Primary,
    onPrimary = OnPrimary,

    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,

    background = Background,
    onBackground = OnBackground,

    surface = Surface,
    onSurface = OnSurface,

    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    outline = Outline,
    outlineVariant = OutlineVariant,

    secondary = Secondary,
    onSecondary = OnSecondary,

    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,

    error = Error,
    onError = OnError,

    scrim = Scrim
)

internal val LocalMainColors = staticCompositionLocalOf {
    lightMainColors
}