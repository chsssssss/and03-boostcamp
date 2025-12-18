package com.boostcamp.and03.ui.screen.prototype.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.boostcamp.and03.ui.screen.prototype.model.MemoGraph
import com.boostcamp.and03.ui.screen.prototype.model.MemoNode

class CanvasViewModel: ViewModel() {
    var graph by mutableStateOf(MemoGraph().apply {
        addMemo(MemoNode("1", "인물 A", "설명 A", Offset(100f, 200f)))
        addMemo(MemoNode("2", "인물 B", "설명 B", Offset(700f, 200f)))
        addMemo(MemoNode("3", "인물 C", "설명 C", Offset(100f, 600f)))
        addMemo(MemoNode("4", "인물 D", "설명 D", Offset(700f, 600f)))
        addMemo(MemoNode("5", "인물 E", "설명 E", Offset(100f, 1000f)))
        addMemo(MemoNode("6", "인물 F", "설명 F", Offset(700f, 1000f)))
    })
    var selectedIds by mutableStateOf<List<String>>(emptyList())
    var connectMode by mutableStateOf(false)
    var panOffset by mutableStateOf(Offset.Zero)
    var scale by mutableStateOf(1f)
    val minScale = 0.5f
    val maxScale = 2.0f

    fun updateViewport(pan: Offset, zoom: Float) {
        panOffset += pan
        scale = (scale * zoom).coerceIn(minScale, maxScale)
    }


}