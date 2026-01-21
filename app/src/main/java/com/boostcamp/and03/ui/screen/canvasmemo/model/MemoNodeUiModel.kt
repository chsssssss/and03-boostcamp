package com.boostcamp.and03.ui.screen.canvasmemo.model

import androidx.compose.ui.unit.IntSize
import com.boostcamp.and03.domain.model.MemoNode

data class MemoNodeUiModel(
    val node: MemoNode,        // domain의 순수 데이터
    val isSelected: Boolean,   // 선택 여부
    val isDragging: Boolean,    // 드래그 여부
    val size: IntSize = IntSize.Zero
)