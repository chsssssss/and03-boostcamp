package com.boostcamp.and03.data.mapper

import com.boostcamp.and03.data.model.response.memo.*

object MemoResponseMapper {
    fun fromFirebase(documentId: String, data: Map<String, Any>): MemoResponse? {
        val type = data["type"] as? String ?: return null
        return when (type) {
            "TEXT" -> parseTextMemo(documentId, data)
            "CANVAS" -> parseCanvasMemo(documentId, data)
            else -> null
        }
    }

    private fun parseTextMemo(
        id: String,
        data: Map<String, Any>
    ): TextMemoResponse =
        TextMemoResponse(
            id = id,
            title = data["title"] as? String ?: "",
            content = data["content"] as? String ?: "",
            createdAt = data["createdAt"] as? String ?: "",
            type = "TEXT",
            startPage = (data["startPage"] as? Long)?.toInt() ?: 0,
            endPage = (data["endPage"] as? Long)?.toInt() ?: 0,
        )

    private fun parseCanvasMemo(
        id: String,
        data: Map<String, Any>
    ): CanvasMemoResponse =
        @Suppress("UNCHECKED_CAST")
        CanvasMemoResponse(
            id = id,
            title = data["title"] as? String ?: "",
            createdAt = data["createdAt"] as? String ?: "",
            type = "CANVAS",
            startPage = (data["startPage"] as? Long)?.toInt() ?: 0,
            endPage = (data["endPage"] as? Long)?.toInt() ?: 0,
            graph = parseGraph(data["graph"] as? Map<String, Any>)
        )

    private fun parseGraph(data: Map<String, Any>?): GraphResponse {
        if (data == null) {
            return GraphResponse(emptyList(), emptyList())
        }

        @Suppress("UNCHECKED_CAST")
        val nodes = (data["nodes"] as? List<Map<String, Any>>).orEmpty().map {
            NodeResponse(
                id = it["id"] as? String ?: "",
                title = it["title"] as? String ?: "",
                content = it["content"] as? String ?: "",
                nodeType = it["nodeType"] as? String ?: "",
                startPage = (it["startPage"] as? Long)?.toInt(),
                endPage = (it["endPage"] as? Long)?.toInt(),
                imageUrl = it["imageUrl"] as? String ?: "",
                x = (it["x"] as? Double)?.toFloat() ?: 0f,
                y = (it["y"] as? Double)?.toFloat() ?: 0f,
            )
        }

        @Suppress("UNCHECKED_CAST")
        val edges = (data["edges"] as? List<Map<String, Any>>).orEmpty().map {
            EdgeResponse(
                id = it["id"] as? String ?: "",
                fromNodeId = it["fromNodeId"] as? String ?: "",
                toNodeId = it["toNodeId"] as? String ?: "",
                relationText = it["relationText"] as? String ?: ""
            )
        }

        return GraphResponse(nodes, edges)
    }
}