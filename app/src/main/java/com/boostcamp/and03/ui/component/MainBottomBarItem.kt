package com.boostcamp.and03.ui.component


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class MainBottomBarItem(
    val type: MainBottomBarType,
    val label: String,
    val icon: ImageVector,
    val backgroundColor: Color
)
