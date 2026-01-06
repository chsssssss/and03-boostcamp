package com.boostcamp.and03.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class And03Shapes(
    val defaultCorner: RoundedCornerShape,
    val dialogCorner: RoundedCornerShape,
    val searchTextFieldCorner: RoundedCornerShape
)

internal val and03Shapes = And03Shapes(
    defaultCorner = RoundedCornerShape(8.dp),
    dialogCorner = RoundedCornerShape(16.dp),
    searchTextFieldCorner = RoundedCornerShape(12.dp)
)

internal val LocalAnd03Shapes = staticCompositionLocalOf {
    and03Shapes
}