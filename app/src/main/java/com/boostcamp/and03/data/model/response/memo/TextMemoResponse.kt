package com.boostcamp.and03.data.model.response.memo

data class TextMemoResponse(
    override val id: String = "",
    override val title: String = "",
    val content: String = "",
    override val createdAt: String = "",
    override val type: String = "",
    val startPage: Int = 0,
    val endPage: Int = 0,
): MemoResponse