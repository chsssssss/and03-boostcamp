package com.boostcamp.and03.domain.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

data class MemoNode(
    val id: String,
    val title: String,    // 메모 제목
    val content: String,    // 메모 내용
    val offset: Offset,    // 캔버스 내부 위치
    val size: IntSize = IntSize.Zero
)