package com.boostcamp.and03.ui.util

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun Color.Companion.random() : Color {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    return Color(red, green, blue)
}