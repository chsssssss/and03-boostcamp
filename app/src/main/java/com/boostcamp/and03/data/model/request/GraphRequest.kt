package com.boostcamp.and03.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class GraphRequest(
    val nodes: List<NodeRequest>,
    val edges: List<EdgeRequest>
)


@Serializable
sealed class NodeRequest {
    abstract val nodeType: String
    abstract val x: Float
    abstract val y: Float

    @Serializable
    data class Character(
        val title: String,
        val content: String,
        override val x: Float,
        override val y: Float,
        override val nodeType: String = "CHARACTER"
    ) : NodeRequest()

    @Serializable
    data class Quote(
        val content: String,
        val page: Int,
        override val x: Float,
        override val y: Float,
        override val nodeType: String = "QUOTE"
    ) : NodeRequest()
}

@Serializable
data class EdgeRequest(
    val fromId: String,
    val toId: String,
    val relationText: String
)