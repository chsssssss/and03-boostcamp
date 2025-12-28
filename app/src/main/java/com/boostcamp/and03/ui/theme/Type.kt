package com.boostcamp.and03.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(

    /* ================= Display ================= */

    displayLarge = TextStyle(
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold
    ),

    displayMedium = TextStyle(
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold
    ),

    displaySmall = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    ),

    /* ================= Headline ================= */

    headlineLarge = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold
    ),

    headlineMedium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold
    ),

    headlineSmall = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    ),

    /* ================= Title ================= */

    titleLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    ),

    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    ),

    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    ),

    /* ================= Body ================= */

    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),

    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),

    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),

    /* ================= Label ================= */

    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    ),

    labelMedium = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    ),

    labelSmall = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.Normal
    )
)