package com.boostcamp.and03.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class MainShapes(
    val defaultCorner: RoundedCornerShape
)

internal val mainShapes = MainShapes(
    defaultCorner = RoundedCornerShape(8.dp)
)

internal val LocalMainShapes = staticCompositionLocalOf {
    mainShapes
}