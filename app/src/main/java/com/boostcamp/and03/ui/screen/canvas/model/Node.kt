package com.boostcamp.and03.ui.screen.canvas.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

data class Node(
    val id: String,
    val text: String,
    val description: String,
    val offset: Offset, // 캔버스 내부 위치
    val size: IntSize = IntSize.Zero // 노드 크기
)

// 화살표 연결 지점 계산
fun Node.rightCenter() = offset + Offset(size.width.toFloat(), size.height / 2f)
fun Node.leftCenter() = offset + Offset(0f, size.height / 2f)