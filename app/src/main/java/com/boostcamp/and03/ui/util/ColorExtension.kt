package com.boostcamp.and03.ui.util

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun Color.Companion.random() : Color {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    return Color(red, green, blue)
}

fun String.toColorOrNull(): Color? {
    return try {
        val hex = removePrefix("#")
        when (hex.length) {
            6 -> Color(0xFF000000 or hex.toLong(16))
            8 -> Color(hex.toLong(16))
            else -> null
        }
    } catch (e: Exception) {
        null
    }
}