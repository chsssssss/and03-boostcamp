package com.boostcamp.and03.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Primary
 * - 핵심 사용자 액션(CTA)
 * - 저장, 확인, 완료 등 주요 버튼
 */
val Primary = Color(0xFF2563EB)            // 기본 상태 배경
val OnPrimary = Color(0xFFFFFFFF)          // Primary 위 텍스트 / 아이콘

/**
 * PrimaryContainer
 * - Primary의 상태 표현용 컨테이너
 * - pressed, selected, 강조 배경
 */
val PrimaryContainer = Color(0xFFEFF3FF)    // 강조 / 선택 배경
val OnPrimaryContainer = Color(0xFF1E3A8A)  // 강조 배경 위 콘텐츠

/**
 * Background
 * - 앱 전체 기본 배경
 */
val Background = Color(0xFFFFFFFF)          // 화면 최상위 배경
val OnBackground = Color(0xFF000000)        // 기본 본문 텍스트

/**
 * Surface
 * - 카드, 시트, Dialog 등 기본 레이아웃 표면
 */
val Surface = Color(0xFFFFFFFF)             // 카드 / Dialog 배경
val OnSurface = Color(0xFF000000)           // 기본 텍스트

/**
 * SurfaceVariant
 * - 보조적인 표면
 * - 입력 필드, 비활성 / 구분 영역
 */
val SurfaceVariant = Color(0xFFF3F4F6)      // TextField 배경
val OnSurfaceVariant = Color(0xFF6C7280)    // placeholder / helper text

/**
 * Outline
 * - 경계선, 구분선
 */
val Outline = Color(0xFFE6E7EB)              // TextField border
val OutlineVariant = Color(0xFF9DA3AF)       // 강조된 border / divider

/**
 * Secondary
 * - 보조 정보 / 덜 중요한 액션
 */
val Secondary = Color(0xFF6C7280)            // 보조 텍스트 / 아이콘
val OnSecondary = Color(0xFFFFFFFF)          // Secondary 위 콘텐츠

/**
 * SecondaryContainer
 * - Secondary 강조 / 배경용
 */
val SecondaryContainer = Color(0xFFF3F4F6)   // 보조 배경
val OnSecondaryContainer = Color(0xFF6C7280) // 보조 배경 위 콘텐츠

/**
 * Error
 * - 오류 상태 표시
 * - validation, 실패 알림
 */
val Error = Color(0xFFDC2626)                // 에러 강조
val OnError = Color(0xFFFFFFFF)              // 에러 위 텍스트

/**
 * Scrim
 * - Modal / BottomSheet 표시 시 배경 차단
 * - 콘텐츠 비활성화 목적
 */
val Scrim = Color(0x99000000)

@Immutable
data class And03Colors(
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

internal val lightAnd03Colors = And03Colors(
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

internal val darkMainColors = And03Colors(
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

internal val LocalAnd03Colors = staticCompositionLocalOf {
    lightAnd03Colors
}