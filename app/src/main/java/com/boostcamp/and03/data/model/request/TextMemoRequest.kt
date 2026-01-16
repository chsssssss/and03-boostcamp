package com.boostcamp.and03.data.model.request

import com.google.firebase.firestore.FieldValue
import kotlinx.serialization.Serializable

@Serializable
data class TextMemoRequest(
    val title: String = "",
    val content: String = "",
    val type: String = "TEXT",
    val startPage: Int = 0,
    val endPage: Int = 0,
)