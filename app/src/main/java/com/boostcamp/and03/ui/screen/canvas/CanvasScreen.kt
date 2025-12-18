package com.boostcamp.and03.ui.screen.canvas

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.screen.canvas.model.Arrow
import com.boostcamp.and03.ui.screen.canvas.model.Node
import com.boostcamp.and03.ui.screen.canvas.model.leftCenter
import com.boostcamp.and03.ui.screen.canvas.model.rightCenter

@Composable
fun CanvasScreen() {
    // 노드 목록
    var nodes by remember {
        mutableStateOf(
            listOf(
                Node("1", "인물 A", "설명 A", Offset(100f, 200f)),
                Node("2", "인물 B", "설명 B", Offset(700f, 200f)),
                Node("3", "인물 C", "설명 C", Offset(100f, 600f)),
                Node("4", "인물 D", "설명 D", Offset(700f, 600f)),
                Node("5", "인물 E", "설명 E", Offset(100f, 1000f)),
                Node("6", "인물 F", "설명 F", Offset(700f, 1000f)),
            )
        )
    }

    var arrows by remember { mutableStateOf<List<Arrow>>(emptyList()) }         // 관계 목록
    var selectedIds by remember { mutableStateOf<List<String>>(emptyList()) }   // 선택된 아이템 아이디 목록
    var connectMode by remember { mutableStateOf(false) }                       // 관계 연결 모드 상태
    var panOffset by remember { mutableStateOf(Offset(0f, 0f)) }      // 캔버스 드래그
    var scale by remember { mutableStateOf(1f) }                                // 1.0 = 100%
    val minScale = 0.001f
    val maxScale = 2.0f

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = if (!connectMode) "연결하기" else "연결 취소",
                modifier = Modifier
                    .padding(16.dp)
                    .combinedClickable {
                        connectMode = !connectMode
                        selectedIds = emptyList()
                    }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = (scale * zoom).coerceIn(minScale, maxScale)
                            panOffset += pan
                        }
                    }
            ) {
                ArrowCanvas(
                    arrows = arrows,
                    nodes = nodes,
                    panOffset = panOffset
                )

                nodes.forEach { item ->
                    DraggableNode(
                        node = item,
                        scale = scale,
                        isSelected = selectedIds.contains(item.id),
                        panOffset = panOffset,
                        onClick = {
                            if (connectMode) {
                                selectedIds = if (selectedIds.contains(item.id)) { // 이미 선택된 아이템이면
                                    selectedIds - item.id
                                } else {
                                    (selectedIds + item.id).takeLast(2)
                                }

                                // 연결
                                if (selectedIds.size == 2) {
                                    arrows = arrows + Arrow(
                                        selectedIds[0],
                                        selectedIds[1]
                                    )
                                    selectedIds = emptyList()
                                    connectMode = false
                                }
                            }
                        },
                        onMove = { newOffset ->
                            nodes = nodes.map {
                                if (it.id == item.id) it.copy(offset = newOffset)
                                else it
                            }
                        },
                        onSizeChanged = { newSize ->
                            nodes = nodes.map {
                                if (it.id == item.id) it.copy(size = newSize)
                                else it
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DraggableNode(
    node: Node,
    scale: Float,
    isSelected: Boolean,
    panOffset: Offset,
    onClick: () -> Unit,
    onMove: (Offset) -> Unit,
    onSizeChanged: (IntSize) -> Unit
) {
    val latestOffset by rememberUpdatedState(node.offset)

    Box(
        modifier = Modifier
            // offset으로 이동, 캔버스 오프셋이랑 아이템 오프셋을 같이 적용
            .graphicsLayer {
                translationX = node.offset.x + panOffset.x
                translationY = node.offset.y + panOffset.y
            }
            // 아이템 이동하면서 선도 같이 움직이도록
            .onGloballyPositioned { coords ->
                Log.d("TAG", "onGloballyPositioned: $coords")
                onSizeChanged(coords.size)
            }
            // 클릭
            .combinedClickable(onClick = onClick)
            // 드래그 제스처
            .pointerInput(node.id) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    Log.d("TAG", "dragAmount: $dragAmount")
                    Log.d("TAG", "item.offset: ${node.offset}")
                    Log.d("TAG", "item.offset + dragAmount: ${node.offset + dragAmount}")

                    onMove(latestOffset + dragAmount)
                }
            }
            .background(
                if (isSelected) Color(0xFFBBDEFB) else Color.White,
                RoundedCornerShape(8.dp)
            )
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column {
            Row {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_account_filled),
                    contentDescription = null
                )
                Spacer(Modifier.width(4.dp))
                Text(text = node.text)
            }
            Text(text = node.description)
        }
    }
}

// 화살표 캔버스
@Composable
fun ArrowCanvas(
    arrows: List<Arrow>,
    nodes: List<Node>,
    panOffset: Offset
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        arrows.forEach { arrow ->
            val from = nodes.firstOrNull { it.id == arrow.fromId }
            val to = nodes.firstOrNull { it.id == arrow.toId }

            if (from != null && to != null) {
                drawLine(
                    color = Color.Black,
                    start = from.rightCenter() + panOffset,
                    end = to.leftCenter() + panOffset,
                    strokeWidth = 4f
                )
            }
        }
    }
}