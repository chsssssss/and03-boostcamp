package com.boostcamp.and03.ui.screen.prototype.screen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.screen.prototype.model.Edge
import com.boostcamp.and03.ui.screen.prototype.model.MemoNode
import com.boostcamp.and03.ui.screen.prototype.model.leftCenter
import com.boostcamp.and03.ui.screen.prototype.model.rightCenter
import com.boostcamp.and03.ui.screen.prototype.navigation.PrototypeRoute
import com.boostcamp.and03.ui.screen.prototype.viewmodel.CanvasViewModel

@Composable
fun CanvasScreen(
    navController: NavController,
    onEditMemoClick: () -> Unit,
    viewModel: CanvasViewModel
) {
    var items = viewModel.graph // 노드와 엣지가 있는 그래프
    var selectedIds = viewModel.selectedIds // 선택된 아이템 아이디 목록
    var connectMode = viewModel.connectMode  // 관계 연결 모드 상태
    var panOffset = viewModel.panOffset // 캔버스 드래그
    var scale = viewModel.scale

    var nodeSizes by remember { mutableStateOf(mapOf<String, IntSize>()) } // 아이템들의 실시간 크기를 저장

//    // 화면 중앙 정렬용 로직
//    var canvasSize by remember { mutableStateOf(IntSize.Zero) } // 캔버스 크기

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
                .padding(top = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { viewModel.toggleConnectMode() }) {
                    Text(if (connectMode) "연결 모드 취소" else "연결 모드로 전환")
                }

                Button(onClick = { viewModel.snapToGrid()}) {
                    Text("격자 정렬")
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
//                    .onGloballyPositioned { coordinates ->
//                        canvasSize = coordinates.size
//                    }
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
                        onClick = { viewModel.selectNode(item.id) },
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
                val start = fromNode.offset + Offset(fromSize.width.toFloat(), fromSize.height / 2f) + panOffset
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

//fun calculateGraphBounds(nodes: Collection<MemoNode>): Pair<Offset, Offset> {
//    val minX = nodes.minOf { it.offset.x }
//    val minY = nodes.minOf { it.offset.y }
//    val maxX = nodes.maxOf { it.offset.x + it.size.width }
//    val maxY = nodes.maxOf { it.offset.y + it.size.height }
//
//    return Offset(minX, minY) to Offset(maxX, maxY)
//}