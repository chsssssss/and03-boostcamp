package com.boostcamp.and03.ui.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.drawVerticalScrollbar(
    state: LazyListState,
    width: Dp = 4.dp,
    color: Color = Color.Gray.copy(alpha = 0.5f)
): Modifier = drawWithContent {
    drawContent()

    val layoutInfo = state.layoutInfo
    val visibleItems = layoutInfo.visibleItemsInfo

    if (visibleItems.isEmpty()) return@drawWithContent

    val totalItemsCount = layoutInfo.totalItemsCount
    val visibleItemsCount = visibleItems.size

    if (totalItemsCount <= visibleItemsCount) return@drawWithContent

    val firstVisibleIndex = visibleItems.first().index

    val elementHeight = size.height / totalItemsCount
    val scrollbarHeight = visibleItemsCount * elementHeight
    val scrollbarOffsetY = firstVisibleIndex * elementHeight

    drawRect(
        color = color,
        topLeft = Offset(
            x = size.width - width.toPx(),
            y = scrollbarOffsetY
        ),
        size = Size(
            width = width.toPx(),
            height = scrollbarHeight
        )
    )
}
