package com.boostcamp.and03.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Primary(메인 액션)
val Primary = Color(0xFF2563EB) // 저장하기 버튼
val OnPrimary = Color(0xFFFFFFFF)

val PrimaryContainer = Color(0xFFEFF3FF)  // 버튼 pressed / 강조 배경
val OnPrimaryContainer = Color(0xFF1E3A8A)

// Background
val Background = Color(0xFFFFFFFF)
val OnBackground = Color(0xFF000000)

// Surface(카드, 입력 영역)
val Surface = Color(0xFFFFFFFF)
val OnSurface = Color(0xFF000000)

val SurfaceVariant = Color(0xFFF3F4F6) // TextField 배경
val OnSurfaceVariant = Color(0xFF6C7280) // placeholder / 보조 텍스트

// Outline
val Outline = Color(0xFFE6E7EB) // TextField border
val OutlineVariant = Color(0xFF9DA3AF)

// Secondary(보조 정보)
val Secondary = Color(0xFF6C7280)
val OnSecondary = Color(0xFFFFFFFF)

val SecondaryContainer = Color(0xFFF3F4F6)
val OnSecondaryContainer = Color(0xFF6C7280)

// Error
val Error = Color(0xFFDC2626)
val OnError = Color(0xFFFFFFFF)

// Scrim(팝업 시 뒷배경 처리를 위한 필드)
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