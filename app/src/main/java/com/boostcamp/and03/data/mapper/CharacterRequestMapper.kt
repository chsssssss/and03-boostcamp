package com.boostcamp.and03.data.mapper

import com.boostcamp.and03.data.model.request.CharacterRequest
import com.boostcamp.and03.data.model.request.ProfileType
import com.google.firebase.firestore.FieldValue

fun CharacterRequest.toEntity(
    id: String,
    imageUrl: String?
): Map<String, Any> {
    val map = mutableMapOf<String, Any>(
        "id" to id,
        "name" to name,
        "role" to role,
        "description" to description,
        "profileType" to profileType.name
    )
 // 이미지가 없으면 필드없이 보내기.
    if (profileType == ProfileType.IMAGE && imageUrl != null) {
        map["imageUrl"] = imageUrl
    }

    if (profileType == ProfileType.COLOR && profileColor != null) {
        map["profileColor"] = profileColor
    }

    return map
}
