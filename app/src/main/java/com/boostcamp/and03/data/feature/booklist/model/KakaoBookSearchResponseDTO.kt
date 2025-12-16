package com.boostcamp.and03.data.feature.booklist.model

import kotlinx.serialization.SerialName

data class KakaoBookSearchResponseDTO(
    @SerialName("meta") val meta: KakaoMetaDTO,
    @SerialName("documents") val documents: List<KakaoDocumentDTO>
)