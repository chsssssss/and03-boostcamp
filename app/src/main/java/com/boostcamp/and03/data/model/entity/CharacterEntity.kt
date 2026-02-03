package com.boostcamp.and03.data.model.entity

import com.boostcamp.and03.data.model.request.ProfileType

data class CharacterEntity(
    val name: String,
    val description: String,
    val profileType: ProfileType,
    val imageUrl: String?,
    val profileColor: String?,
    val createdAt: Long = System.currentTimeMillis()
)