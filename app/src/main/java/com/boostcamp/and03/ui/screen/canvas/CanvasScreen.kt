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
import androidx.compose.runtime.LaunchedEffect
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
import com.boostcamp.and03.ui.screen.canvas.model.Edge
import com.boostcamp.and03.ui.screen.canvas.model.MemoGraph
import com.boostcamp.and03.ui.screen.canvas.model.MemoNode
import com.boostcamp.and03.ui.screen.canvas.model.leftCenter
import com.boostcamp.and03.ui.screen.canvas.model.rightCenter

@Composable
fun CanvasScreen() {

    var items by remember {
        mutableStateOf(MemoGraph().apply {
            addMemo(MemoNode("1", "인물 A", "설명 A", Offset(100f, 200f)))
            addMemo(MemoNode("2", "인물 B", "설명 B", Offset(700f, 200f)))
            addMemo(MemoNode("3", "인물 C", "설명 C", Offset(100f, 600f)))
            addMemo(MemoNode("4", "인물 D", "설명 D", Offset(700f, 600f)))
            addMemo(MemoNode("5", "인물 E", "설명 E", Offset(100f, 1000f)))
            addMemo(MemoNode("6", "인물 F", "설명 F", Offset(700f, 1000f)))
        })
    }

    var nodeSizes by remember { mutableStateOf(mapOf<String, IntSize>()) }      // 아이템들의 실시간 크기를 저장
    var selectedIds by remember { mutableStateOf<List<String>>(emptyList()) }   // 선택된 아이템 아이디 목록
    var connectMode by remember { mutableStateOf(false) }                       // 관계 연결 모드 상태
    var panOffset by remember { mutableStateOf(Offset(0f, 0f)) }      // 캔버스 드래그
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }                 // 캔버스 크기

    var scale by remember { mutableStateOf(1f) } // 현재 배율, 1.0 = 100%
    val minScale = 0.1f                                  // 최소 배율
    val maxScale = 2.0f                                  // 최대 배율

    LaunchedEffect(canvasSize, nodeSizes) {
        val allSized = items.nodes.keys.all { id ->
            nodeSizes[id]?.let { it != IntSize.Zero } == true
        }

        if (canvasSize != IntSize.Zero && allSized) {
            val (min, max) = calculateGraphBounds(
                items.nodes.values.map { node ->
                    node.copy(size = nodeSizes[node.id]!!)
                }
            )

            val graphCenter = Offset(
                (min.x + max.x) / 2f,
                (min.y + max.y) / 2f
            )

            val screenCenter = Offset(
                canvasSize.width / 2f,
                canvasSize.height / 2f
            )

            panOffset = screenCenter - graphCenter
        }
    }

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
                    .onGloballyPositioned { coords ->
                        canvasSize = coords.size
                    }
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
                    arrows = items.edges,
                    items = items.nodes,
                    nodeSizes = nodeSizes,
                    panOffset = panOffset
                )

                items.nodes.values.forEach { item ->
                    DraggableItem(
                        item = item,
                        isSelected = selectedIds.contains(item.id),
                        panOffset = panOffset,
                        onClick = {
                            if (connectMode) {
                                selectedIds =
                                    if (selectedIds.contains(item.id))  // 이미 선택된 아이템이면
                                        selectedIds - item.id
                                    else
                                        (selectedIds + item.id).takeLast(2)

                                // 연결
                                if (selectedIds.size == 2) {
                                    val newGraph = MemoGraph().apply {
                                        items.nodes.values.forEach { addMemo(it) }
                                        items.edges.forEach { connectMemo(it.fromId, it.toId, it.name) }
                                        connectMemo(selectedIds[0], selectedIds[1], "연결")
                                    }
                                    items = newGraph
                                    selectedIds = emptyList()
                                    connectMode = false
                                }
                            }
                        },
                        onMove = { newOffset ->
                            val newGraph = MemoGraph().apply {
                                items.nodes.values.forEach { node ->
                                    if (node.id == item.id) addMemo(node.copy(offset = newOffset))
                                    else addMemo(node)
                                }
                                items.edges.forEach { connectMemo(it.fromId, it.toId, it.name) }
                            }
                            items = newGraph
                        },
                        onSizeChanged = { newSize ->
                            nodeSizes = nodeSizes + (item.id to newSize)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DraggableItem(
    item: MemoNode,
    isSelected: Boolean,
    panOffset: Offset,
    onClick: () -> Unit,
    onMove: (Offset) -> Unit,
    onSizeChanged: (IntSize) -> Unit
) {
    val latestOffset by rememberUpdatedState(item.offset)

    Box(
        modifier = Modifier
            // offset으로 이동, 캔버스 오프셋이랑 아이템 오프셋을 같이 적용
            .graphicsLayer {
                translationX = item.offset.x + panOffset.x
                translationY = item.offset.y + panOffset.y
            }
            // 아이템 이동하면서 선도 같이 움직이도록
            .onGloballyPositioned { coords ->
                onSizeChanged(coords.size)
            }
            // 클릭
            .combinedClickable(onClick = onClick)
            // 드래그 제스처
            .pointerInput(item.id) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    Log.d("TAG", "dragAmount: $dragAmount")
                    Log.d("TAG", "item.offset: ${item.offset}")
                    Log.d("TAG", "item.offset + dragAmount: ${item.offset + dragAmount}")

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
                Text(text = item.title)
            }
            Text(text = item.content)
        }
    }
}

// 화살표 캔버스
@Composable
fun ArrowCanvas(
    arrows: List<Edge>,
    items: Map<String, MemoNode>,
    nodeSizes: Map<String, IntSize>,
    panOffset: Offset
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        arrows.forEach { edge ->
//            val from = items.firstOrNull { it.id == arrow.fromId }
//            val to = items.firstOrNull { it.id == arrow.toId }

            val fromNode = items[edge.fromId]
            val toNode = items[edge.toId]

            if (fromNode != null && toNode != null) {
                drawLine(
                    color = Color.Black,
                    start = fromNode.rightCenter() + panOffset,
                    end = toNode.leftCenter() + panOffset,
                    strokeWidth = 4f
                )
            }
        }
    }
}

fun calculateGraphBounds(nodes: Collection<MemoNode>): Pair<Offset, Offset> {
    val minX = nodes.minOf { it.offset.x }
    val minY = nodes.minOf { it.offset.y }
    val maxX = nodes.maxOf { it.offset.x + it.size.width }
    val maxY = nodes.maxOf { it.offset.y + it.size.height }

    return Offset(minX, minY) to Offset(maxX, maxY)
}