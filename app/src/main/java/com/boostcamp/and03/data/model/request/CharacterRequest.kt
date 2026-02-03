package com.boostcamp.and03.data.model.request

import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import kotlinx.serialization.Serializable

enum class ProfileType { IMAGE, COLOR }
@Serializable
data class CharacterRequest(
    val role: String = "",
    val description: String = "",
    val name: String = "",
    val profileType: ProfileType = ProfileType.COLOR,
    val profileColor: String? = null,
    val profileImgUri: String? = null
)
fun CharacterUiModel.toRequest() = CharacterRequest(
    role = role,
    description = description,
    name = name,
)