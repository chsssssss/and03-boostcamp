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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.screen.prototype.model.Edge
import com.boostcamp.and03.ui.screen.prototype.model.MemoGraph
import com.boostcamp.and03.ui.screen.prototype.model.MemoNode
import com.boostcamp.and03.ui.screen.prototype.model.leftCenter
import com.boostcamp.and03.ui.screen.prototype.model.rightCenter
import com.boostcamp.and03.ui.screen.prototype.navigation.PrototypeRoute
import com.boostcamp.and03.ui.screen.prototype.viewmodel.CanvasViewModel

@Composable
fun CanvasScreen(
    navController: NavController,
    onEditMemoClick: () -> Unit,
    viewModel: CanvasViewModel = viewModel()
) {
    var items = viewModel.graph // 노드와 엣지가 있는 그래프
    var selectedIds = viewModel.selectedIds // 선택된 아이템 아이디 목록
    var connectMode = viewModel.connectMode  // 관계 연결 모드 상태
    var panOffset = viewModel.panOffset // 캔버스 드래그
    var scale = viewModel.scale

    var nodeSizes by remember { mutableStateOf(mapOf<String, IntSize>()) } // 아이템들의 실시간 크기를 저장
    var canvasSize by remember { mutableStateOf(IntSize.Zero) } // 캔버스 크기

//    LaunchedEffect(canvasSize, nodeSizes) {
//        val allSized = items.nodes.keys.all { id ->
//            nodeSizes[id]?.let { it != IntSize.Zero } == true
//        }
//
//        if (canvasSize != IntSize.Zero && allSized) {
//            val (min, max) = calculateGraphBounds(
//                items.nodes.values.map { node ->
//                    node.copy(size = nodeSizes[node.id]!!)
//                }
//            )
//
//            val graphCenter = Offset(
//                (min.x + max.x) / 2f,
//                (min.y + max.y) / 2f
//            )
//
//            val screenCenter = Offset(
//                canvasSize.width / 2f,
//                canvasSize.height / 2f
//            )
//
//            panOffset = screenCenter - graphCenter
//        }
//    }

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
    ) { innerPadding ->
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
                    .onGloballyPositioned { coordinates ->
                        canvasSize = coordinates.size
                    }
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            viewModel.updateViewport(pan, zoom)
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
                            viewModel.moveNode(item.id, newOffset)
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