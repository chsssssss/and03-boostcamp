package com.boostcamp.and03.domain.model

import androidx.compose.ui.geometry.Offset

sealed class MemoNode {
    abstract val id: String
    abstract val offset: Offset     // 캔버스 내부 위치

    data class CharacterNode(
        override val id: String,
        val name: String,
        val description: String,
        override val offset: Offset,
    ) : MemoNode()

    data class QuoteNode(
        override val id: String,
        val content: String,
        val page: Int,
        override val offset: Offset,
    ) : MemoNode()
}