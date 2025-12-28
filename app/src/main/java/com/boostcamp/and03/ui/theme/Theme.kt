package com.boostcamp.and03.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

//val LightColorScheme = lightColorScheme(
//    primary = Color(0xFF6750A4),
//    onPrimary = Color.White,
//    primaryContainer = Color(0xFFEADDFF),
//    onPrimaryContainer = Color(0xFF21005D),
//
//    secondary = Color(0xFF625B71),
//    onSecondary = Color.White,
//    secondaryContainer = Color(0xFFE8DEF8),
//    onSecondaryContainer = Color(0xFF1D192B),
//
//    tertiary = Color(0xFF7D5260),
//    onTertiary = Color.White,
//    tertiaryContainer = Color(0xFFFFD8E4),
//    onTertiaryContainer = Color(0xFF31111D),
//
//    background = Color(0xFFFFFBFE),
//    onBackground = Color(0xFF1C1B1F),
//
//    surface = Color(0xFFFFFBFE),
//    onSurface = Color(0xFF1C1B1F),
//    surfaceVariant = Color(0xFFE7E0EC),
//    onSurfaceVariant = Color(0xFF49454F),
//
//    error = Color(0xFFB3261E),
//    onError = Color.White,
//    errorContainer = Color(0xFFF9DEDC),
//    onErrorContainer = Color(0xFF410E0B),
//
//    outline = Color(0xFF79747E),
//    scrim = Color(0x99000000)
//)

val LightColorScheme = lightColorScheme(
    /* Primary (메인 액션) */
    primary = Color(0xFF2563EB),          // 저장하기 버튼
    onPrimary = Color(0xFFFFFFFF),

    primaryContainer = Color(0xFFEFF3FF), // 버튼 pressed / 강조 배경
    onPrimaryContainer = Color(0xFF1E3A8A),

    /* Background */
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),

    /* Surface (카드, 입력 영역) */
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),

    surfaceVariant = Color(0xFFF3F4F6),   // TextField 배경
    onSurfaceVariant = Color(0xFF6C7280), // placeholder / 보조 텍스트

    /* Outline */
    outline = Color(0xFFE6E7EB),           // TextField border
    outlineVariant = Color(0xFF9DA3AF),

    /* Secondary (보조 정보) */
    secondary = Color(0xFF6C7280),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFF3F4F6),
    onSecondaryContainer = Color(0xFF6C7280),

    /* Error (아직 화면엔 없지만 대비용) */
    error = Color(0xFFDC2626),
    onError = Color.White,

    /* Scrim */
    scrim = Color(0x99000000)
)

@Composable
fun And03Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}