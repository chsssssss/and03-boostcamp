package com.boostcamp.and03.data.mapper

import com.boostcamp.and03.data.model.request.EdgeRequest
import com.boostcamp.and03.data.model.request.GraphRequest
import com.boostcamp.and03.data.model.request.NodeRequest
import com.boostcamp.and03.data.model.request.ProfileType
import com.boostcamp.and03.domain.model.Edge
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.domain.model.MemoNode

fun MemoNode.toRequest(): NodeRequest {
    return when (this) {
        is MemoNode.CharacterNode -> NodeRequest.Character(
            id = this.id,
            title = this.name,
            content = this.description,
            nodeType = "CHARACTER",
            x = this.offset.x,
            y = this.offset.y,
            profileType = profileType.name,
            profileColor = this.iconColor,
            imageUrl = this.imageUrl
        )

        is MemoNode.QuoteNode -> NodeRequest.Quote(
            id = this.id,
            content = this.content,
            nodeType = "QUOTE",
            page = this.page,
            x = this.offset.x,
            y = this.offset.y,
        )
    }
}

fun NodeRequest.toFirestoreMap(): Map<String, Any?> {
    return when (this) {
        is NodeRequest.Character -> mapOf(
            "id" to id,
            "nodeType" to nodeType,
            "x" to x,
            "y" to y,
            "title" to title,
            "content" to content,
            "profileType" to profileType,
            "profileColor" to profileColor,
            "imageUrl" to imageUrl,
        )

        is NodeRequest.Quote -> mapOf(
            "id" to id,
            "nodeType" to nodeType,
            "x" to x,
            "y" to y,
            "content" to content,
            "page" to page,
        )
    }
}

fun Edge.toRequest(): EdgeRequest {
    return EdgeRequest(
        id = this.id,
        fromId = this.fromId,
        toId = this.toId,
        relationText = this.name
    )
}

fun MemoGraph.toRequest(): GraphRequest {
    return GraphRequest(
        nodes = nodes.values.map { it.toRequest() },
        edges = edges.map { it.toRequest() }
    )
}
