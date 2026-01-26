package com.boostcamp.and03.ui.screen.canvasmemo.model

import androidx.compose.ui.unit.IntSize
import com.boostcamp.and03.domain.model.MemoNode

sealed class MemoNodeUiModel {
    abstract val node: MemoNode
    abstract val isSelected: Boolean
    abstract val isDragging: Boolean
    abstract val size: IntSize

    data class CharacterNodeUiModel(
        override val node: MemoNode.CharacterNode,
        override val isSelected: Boolean,
        override val isDragging: Boolean,
        override val size: IntSize = IntSize.Zero
    ) : MemoNodeUiModel()

    data class QuoteNodeUiModel(
        override val node: MemoNode.QuoteNode,
        override val isSelected: Boolean,
        override val isDragging: Boolean,
        override val size: IntSize = IntSize.Zero
    ) : MemoNodeUiModel()
}

fun MemoNode.toUiModel(
    isSelected: Boolean = false,
    isDragging: Boolean = false,
    size: IntSize = IntSize.Zero
): MemoNodeUiModel {
    return when (this) {
        is MemoNode.CharacterNode -> MemoNodeUiModel.CharacterNodeUiModel(
            this,
            isSelected,
            isDragging,
            size
        )

        is MemoNode.QuoteNode -> MemoNodeUiModel.QuoteNodeUiModel(
            this,
            isSelected,
            isDragging,
            size
        )
    }
}