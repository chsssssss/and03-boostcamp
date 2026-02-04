package com.boostcamp.and03.data.mapper

import android.util.Log
import com.boostcamp.and03.data.model.response.memo.*

object MemoResponseMapper {
    fun fromFirebase(documentId: String, data: Map<String, Any>): MemoResponse? {
        val type = data["type"] as? String ?: return null
        Log.d("MemoResponseMapper", "Parsing memo type: $type")
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
                page = (it["page"] as? Long)?.toInt() ?: 0,
                imageUrl = it["imageUrl"] as? String,
                x = (it["x"] as? Double)?.toFloat() ?: 0f,
                y = (it["y"] as? Double)?.toFloat() ?: 0f,
                profileType = it["profileType"] as? String,
                iconColor = it["iconColor"] as? String,
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

object MemoNodeMapper {
    fun fromFirebase(
        id: String,
        data: Map<String, Any>
    ): NodeResponse =
        NodeResponse(
            id = id,
            title = data["title"] as? String ?: "",
            content = data["content"] as? String ?: "",
            nodeType = data["nodeType"] as? String ?: "",
            page = (data["page"] as? Long)?.toInt() ?: 0,
            imageUrl = data["imageUrl"] as? String,
            x = (data["x"] as? Double)?.toFloat() ?: 0f,
            y = (data["y"] as? Double)?.toFloat() ?: 0f,
            profileType = data["profileType"] as? String,
            iconColor = data["iconColor"] as? String,
        )
}

object MemoEdgeMapper {
    fun fromFirebase(
        id: String,
        data: Map<String, Any>
    ): EdgeResponse =
        EdgeResponse(
            id = id,
            fromNodeId = data["fromId"] as? String ?: "",
            toNodeId = data["toId"] as? String ?: "",
            relationText = data["relationText"] as? String ?: ""
        )
}
