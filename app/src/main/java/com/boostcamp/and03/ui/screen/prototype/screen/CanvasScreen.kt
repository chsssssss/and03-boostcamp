package com.boostcamp.and03.ui.screen.prototype.screen

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.screen.prototype.model.Edge
import com.boostcamp.and03.ui.screen.prototype.model.MemoNode
import com.boostcamp.and03.ui.screen.prototype.navigation.PrototypeRoute
import kotlin.div
import kotlin.plus

@Composable
fun CanvasScreen(
    navController: NavController,
    viewModel: CanvasViewModel = viewModel()
) {
    val items by viewModel.graph.collectAsState()

    var nodeSizes by remember { mutableStateOf(mapOf<String, IntSize>()) }
    var selectedIds by remember { mutableStateOf<List<String>>(emptyList()) }
    var connectMode by remember { mutableStateOf(false) }

    var panOffset by remember { mutableStateOf(Offset.Zero) }

    var scale by remember { mutableStateOf(1f) }
    val minScale = 0.5f
    val maxScale = 2f

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate(PrototypeRoute.MemoEdit)
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_edit_outlined),
                        contentDescription = null
                    )
                },
                text = {
                    Text(text = "메모 작성")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Button(onClick = {
                connectMode = !connectMode
                selectedIds = emptyList()
            }) {
                Text(if (connectMode) "연결 취소" else "연결하기")
            }

            Button(onClick = {
                viewModel.snapToGrid(300f)
            }) {
                Text("격자 정렬")
            }

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
                    arrows = items.edges,
                    items = items.nodes,
                    panOffset = panOffset,
                    nodeSizes = nodeSizes
                )

                items.nodes.values.forEach { item ->
                    DraggableItem(
                        item = item,
                        panOffset = panOffset,
                        isSelected = selectedIds.contains(item.id),
                        onClick = {
                            if (connectMode) {

                                selectedIds =
                                    if (selectedIds.contains(item.id))
                                        selectedIds - item.id
                                    else
                                        (selectedIds + item.id).takeLast(2)

                                if (selectedIds.size == 2) {
                                    viewModel.connect(
                                        selectedIds[0],
                                        selectedIds[1],
                                        "연결"
                                    )
                                    selectedIds = emptyList()
                                    connectMode = false
                                }
                            }
                        },
                        onMove = { delta ->
                            viewModel.moveNode(item.id, delta)
                        },
                        onSizeChanged = { size ->
                            nodeSizes = nodeSizes + (item.id to size)
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
                    onMove(dragAmount)
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
    panOffset: Offset,
    nodeSizes: Map<String, IntSize>,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        arrows.forEach { edge ->

            val fromNode = items[edge.fromId]
            val toNode = items[edge.toId]
            val fromSize = nodeSizes[edge.fromId] ?: IntSize.Zero
            val toSize = nodeSizes[edge.toId] ?: IntSize.Zero

            if (fromNode != null && toNode != null) {
                // 시작점 (출발 노드의 우측 중앙)
                val start = fromNode.offset + Offset(
                    fromSize.width.toFloat(),
                    fromSize.height / 2f
                ) + panOffset
                // 끝점 (도착 노드의 좌측 중앙)
                val end = toNode.offset + Offset(0f, toSize.height / 2f) + panOffset

                // 꺾임 포인트 계산 (중간 지점)
                val midX = (start.x + end.x) / 2

                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(start.x, start.y)
                    // 1. 중간 지점까지 수평 이동
                    lineTo(midX, start.y)
                    // 2. 도착 노드의 Y 높이까지 수직 이동
                    lineTo(midX, end.y)
                    // 3. 도착 지점까지 수평 이동
                    lineTo(end.x, end.y)
                }

                drawPath(
                    path = path,
                    color = Color.Black,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
                )
            }
        }
    }
}

fun snapNodesToGrid(
    nodes: List<MemoNode>,
    gridSize: Float = 100f
): List<MemoNode> {
    return nodes.map { node ->
        val snappedX = (node.offset.x / gridSize).let { kotlin.math.round(it) } * gridSize
        val snappedY = (node.offset.y / gridSize).let { kotlin.math.round(it) } * gridSize
        node.copy(offset = Offset(snappedX, snappedY))
    }
}