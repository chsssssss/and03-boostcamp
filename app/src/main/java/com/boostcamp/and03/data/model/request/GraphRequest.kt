package com.boostcamp.and03.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class GraphRequest(
    val nodes: List<NodeRequest>,
    val edges: List<EdgeRequest>
)


@Serializable
sealed class NodeRequest {
    abstract val id: String
    abstract val nodeType: String
    abstract val x: Float
    abstract val y: Float

    @Serializable
    data class Character(
        override val id: String,
        val title: String,
        val content: String,
        override val x: Float,
        override val y: Float,
        override val nodeType: String = "CHARACTER"
    ) : NodeRequest()

    @Serializable
    data class Quote(
        override val id: String,
        val content: String,
        val page: Int,
        override val x: Float,
        override val y: Float,
        override val nodeType: String = "QUOTE",
    ) : NodeRequest()
}

@Serializable
data class EdgeRequest(
    val id: String,
    val fromId: String,
    val toId: String,
    val relationText: String
)