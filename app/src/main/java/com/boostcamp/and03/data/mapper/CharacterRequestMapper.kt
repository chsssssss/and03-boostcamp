package com.boostcamp.and03.data.mapper

import com.boostcamp.and03.data.model.request.CharacterRequest
import com.boostcamp.and03.data.model.request.ProfileType

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

    if (profileType == ProfileType.IMAGE && imageUrl != null) {
        map["imageUrl"] = imageUrl
    }

    if (profileType == ProfileType.COLOR && profileColor != null) {
        map["profileColor"] = profileColor
    }

    return map
}
