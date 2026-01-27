package com.boostcamp.and03.ui.util

import com.boostcamp.and03.domain.model.MemoNode
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel

inline fun <reified T : MemoNode, R> Map<String, MemoNodeUiModel>.getNodeProperty(
    id: String?,
    extractor: (T) -> R
): R? {
    val node = this[id]?.node
    return if (node is T) {
        extractor(node)
    } else {
        null
    }
}

fun Map<String, MemoNodeUiModel>.getCharacterName(id: String?): String? =
    getNodeProperty<MemoNode.CharacterNode, String>(id) { it.name }

fun Map<String, MemoNodeUiModel>.getCharacterImage(id: String?): String? =
    getNodeProperty<MemoNode.CharacterNode, String>(id) { it.imageUrl }

fun Map<String, MemoNodeUiModel>.getQuoteContent(id: String?): String? =
    getNodeProperty<MemoNode.QuoteNode, String>(id) { it.content }