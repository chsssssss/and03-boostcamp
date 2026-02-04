package com.boostcamp.and03.data.model.response

import com.boostcamp.and03.data.model.request.ProfileType

data class CharacterResponse(
    val id: String,
    val name: String,
    val role: String,
    val description: String,
    val profileType: String,
    val profileImgUri: String?,
    val profileColor: String?
)