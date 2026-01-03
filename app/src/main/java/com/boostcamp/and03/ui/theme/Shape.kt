package com.boostcamp.and03.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class MainShapes(
    val defaultCorner: RoundedCornerShape,
    val dialogCorner: RoundedCornerShape
)

internal val mainShapes = MainShapes(
    defaultCorner = RoundedCornerShape(8.dp),
    dialogCorner = RoundedCornerShape(16.dp)
)

internal val LocalMainShapes = staticCompositionLocalOf {
    mainShapes
}