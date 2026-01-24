package com.boostcamp.and03.ui.screen.canvasmemo

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.component.NodeItem
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolExpandableButton
import com.boostcamp.and03.ui.screen.canvasmemo.model.EdgeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Theme
import kotlin.math.max
import kotlin.math.min

@Composable
fun CanvasMemoRoute(
    navigateToBack: () -> Unit,
    viewModel: CanvasMemoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is CanvasMemoEvent.NavToBack -> navigateToBack()
            }
        }
    }

    CanvasMemoScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun CanvasMemoScreen(
    uiState: CanvasMemoUiState,
    onAction: (CanvasMemoAction) -> Unit,
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }

    val canvasSize = 2000.dp
    val minScale = 0.5f
    val maxScale = 2f

    val maxOffsetRange = 1000f

    var nodeSizes by remember { mutableStateOf<Map<String, IntSize>>(emptyMap()) }

    fun clampOffset(
        newOffset: Offset,
    ): Offset {
        val maxOffset = maxOffsetRange
        val minOffset = -maxOffsetRange

        return Offset(
            x = max(minOffset, min(maxOffset, newOffset.x)),
            y = max(minOffset, min(maxOffset, newOffset.y))
        )
    }

    Scaffold(
        topBar = {
            And03AppBar(
                title = "임시 제목",
                onBackClick = { onAction(CanvasMemoAction.ClickBack) }
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_more_vert_filled),
                        contentDescription = stringResource(
                            R.string.content_description_more_button
                        )
                    )
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            val newScale = (scale * zoom).coerceIn(minScale, maxScale)

                            scale = newScale
                            offset = clampOffset(offset + pan)
                        }
                    }
            ) {
                Box(
                    modifier = Modifier
                        .size(canvasSize)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationX = offset.x
                            translationY = offset.y
                            transformOrigin = TransformOrigin(0f, 0f)
                        }
                ) {

                    Arrows(
                        arrows = uiState.edges,
                        items = uiState.nodes,
                        nodeSizes = nodeSizes
                    )

                    uiState.nodes.forEach { (_, uiModel) ->
                        when (uiModel) {

                            is MemoNodeUiModel.CharacterNodeUiModel -> {
                                DraggableCanvasItem(
                                    nodeId = uiModel.node.id,
                                    worldOffset = uiModel.node.offset,
                                    onMove = { delta ->
                                        onAction(
                                            CanvasMemoAction.MoveNode(
                                                nodeId = uiModel.node.id,
                                                newOffset = delta
                                            )
                                        )
                                    },
                                    onSizeChanged = { size ->
                                        nodeSizes = nodeSizes + (uiModel.node.id to size)
                                    }
                                ) {
                                    NodeItem(
                                        title = uiModel.node.name,
                                        content = uiModel.node.description,
                                        isHighlighted = uiModel.isSelected,
                                        onMoreClick = {}
                                    )
                                }
                            }

                            is MemoNodeUiModel.QuoteNodeUiModel -> {
                                DraggableCanvasItem(
                                    nodeId = uiModel.node.id,
                                    worldOffset = uiModel.node.offset,
                                    onMove = { delta ->
                                        onAction(
                                            CanvasMemoAction.MoveNode(
                                                nodeId = uiModel.node.id,
                                                newOffset = delta
                                            )
                                        )
                                    },
                                    onSizeChanged = { size ->
                                        nodeSizes = nodeSizes + (uiModel.node.id to size)
                                    }
                                ) {
                                    Text(text = uiModel.node.content)
                                }
                            }
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(And03Padding.PADDING_L)
                        .background(And03Theme.colors.surfaceVariant),
                ) {
                    Column(modifier = Modifier.padding(And03Padding.PADDING_M)) {
                        Text(
                            text = "Offset: (${offset.x.toInt()}, ${offset.y.toInt()})",
                            color = And03Theme.colors.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Scale: ${(scale * 100).toInt()}%",
                            color = And03Theme.colors.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

//
//                if (uiState.isRelationDialogVisible) {
//                    RelationEditorDialog(
//                        relationNameState = uiState.relationNameState,
//                        fromName = uiState.fromCharacterName,
//                        toName = uiState.toCharacterName,
//                        onDismiss = { onAction(CanvasMemoAction.CloseRelationDialog) },
//                        onConfirm = { /* 관계 저장 로직 */ },
//                        onFromImageClick = { /* 인물 선택 로직 */ },
//                        onToImageClick = { /* 인물 선택 로직 */ }
//                    )
//                }
//
//                if (uiState.isAddCharacterDialogVisible) {
//                    AddCharacterDialog(
//                        nameState = uiState.characterNameState,
//                        descState = uiState.characterDescState,
//                        enabled = uiState.characterNameState.text.isNotBlank(),
//                        onDismiss = { onAction(CanvasMemoAction.CloseAddCharacterDialog) },
//                        onConfirm = { /* 캐릭터 저장 */ },
//                        onClickAddImage = { /* 이미지 추가 */ }
//                    )
//                }
//            }

            ToolExpandableButton(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = And03Padding.PADDING_L, bottom = And03Padding.PADDING_2XL),
                actions = listOf(
                    ToolAction(
                        iconRes = R.drawable.ic_add_filled,
                        contentDescription = stringResource(R.string.tool_ic_content_desc_zoom_in),
                        onClick = {
                            scale = (scale * 1.2f).coerceIn(minScale, maxScale)
                        }
                    ),
                    ToolAction(
                        iconRes = R.drawable.ic_remove_filled,
                        contentDescription = stringResource(R.string.tool_ic_content_desc_zoom_out),
                        onClick = {
                            scale = (scale / 1.2f).coerceIn(minScale, maxScale)
                        }
                    ),
                    ToolAction(
                        iconRes = R.drawable.ic_fit_screen,
                        contentDescription = stringResource(R.string.tool_ic_content_desc_fit_screen),
                        onClick = {
                            scale = 1f
                            offset = Offset(0f, 0f)
                        }
                    )
                )
            )
        }
    }
}

@Composable
fun DraggableCanvasItem(
    nodeId: String,
    worldOffset: Offset,
    onMove: (Offset) -> Unit,
    onSizeChanged: (IntSize) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                translationX = worldOffset.x
                translationY = worldOffset.y
            }
            .onGloballyPositioned { coords ->
                onSizeChanged(coords.size)
            }
            .pointerInput(nodeId) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    Log.d("DraggableCanvasItem", "dragAmount: $dragAmount")
                    Log.d("DraggableCanvasItem", "worldOffset: $worldOffset")
                    onMove(dragAmount)
                }
            }
    ) {
        content()
    }
}

@Composable
fun Arrows(
    arrows: List<EdgeUiModel>,
    items: Map<String, MemoNodeUiModel>,
    nodeSizes: Map<String, IntSize>,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        arrows.forEach { edge ->

            val fromNode = items[edge.edge.fromId]
            val toNode = items[edge.edge.toId]
            val fromSize = nodeSizes[edge.edge.fromId] ?: IntSize.Zero
            val toSize = nodeSizes[edge.edge.toId] ?: IntSize.Zero

            if (fromNode != null && toNode != null) {

                val start = fromNode.node.offset + Offset(
                    fromSize.width.toFloat(),
                    fromSize.height / 2f
                )

                val end = toNode.node.offset + Offset(
                    0f,
                    toSize.height / 2f
                )

                val midX = (start.x + end.x) / 2

                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(start.x, start.y)
                    lineTo(midX, start.y)
                    lineTo(midX, end.y)
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


@Preview(showBackground = true)
@Composable
fun CanvasMemoScreenPreview() {
    val previewState = CanvasMemoUiState()

    CanvasMemoScreen(
        uiState = previewState,
        onAction = {},
    )

}