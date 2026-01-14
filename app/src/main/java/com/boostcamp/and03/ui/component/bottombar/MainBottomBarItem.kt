package com.boostcamp.and03.ui.component.bottombar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.boostcamp.and03.ui.component.bottombar.MainBottomBarType

data class MainBottomBarItem(
    val type: MainBottomBarType,
    val label: String,
    val icon: ImageVector,
    val backgroundColor: Color
)