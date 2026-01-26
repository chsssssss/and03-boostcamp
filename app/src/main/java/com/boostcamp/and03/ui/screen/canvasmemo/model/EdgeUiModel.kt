package com.boostcamp.and03.ui.screen.canvasmemo.model

import com.boostcamp.and03.domain.model.Edge

data class EdgeUiModel(
    val edge: Edge,
)

fun Edge.toUiModel(): EdgeUiModel {
    return EdgeUiModel(this)
}