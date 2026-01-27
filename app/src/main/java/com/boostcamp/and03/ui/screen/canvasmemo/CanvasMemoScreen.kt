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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PersonAdd
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
import com.boostcamp.and03.ui.screen.canvasmemo.component.AddQuoteBottomSheet
import com.boostcamp.and03.ui.screen.canvasmemo.component.AddQuoteDialog
import com.boostcamp.and03.ui.screen.canvasmemo.component.NodeItem
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolExpandableButton
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBar
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarItem
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
import com.boostcamp.and03.ui.screen.canvasmemo.model.EdgeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.theme.CanvasMemoColors
import kotlin.math.max
import kotlin.math.min

private object CanvasMemoScreenValues {
    val CANVAS_SIZE = 2000.dp
    val MIN_SCALE = 0.5f
    val MAX_SCALE = 2f
    val MAX_OFFSET_RANGE = 1000f
}

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
    var panOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    var nodeSizes by remember { mutableStateOf<Map<String, IntSize>>(emptyMap()) }

    fun clampOffset(
        newOffset: Offset,
    ): Offset {
        val maxOffset = CanvasMemoScreenValues.MAX_OFFSET_RANGE
        val minOffset = -CanvasMemoScreenValues.MAX_OFFSET_RANGE

        return Offset(
            x = max(minOffset, min(maxOffset, newOffset.x)),
            y = max(minOffset, min(maxOffset, newOffset.y))
        )
    }

    Scaffold(
        topBar = {
            And03AppBar(
                title = stringResource(R.string.canvas_memo_top_bar_title),
                onBackClick = { onAction(CanvasMemoAction.ClickBack) }
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_more_vert_filled),
                        contentDescription = stringResource(
                            id = R.string.content_description_more_button
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
                            val newScale = (scale * zoom)
                                .coerceIn(
                                    CanvasMemoScreenValues.MIN_SCALE,
                                    CanvasMemoScreenValues.MAX_SCALE
                                )

                            scale = newScale
                            panOffset = clampOffset(panOffset + pan)
                        }
                    }
            ) {
                Box(
                    modifier = Modifier
                        .size(CanvasMemoScreenValues.CANVAS_SIZE)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationX = panOffset.x
                            translationY = panOffset.y
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
                            text = "Offset: (${panOffset.x.toInt()}, ${panOffset.y.toInt()})",
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

            // BottomSheet 표시
            if (uiState.isAddCharacterBottomSheetVisible) {
                // TODO: 등장인물 추가 바텀시트 표시
            }

            if (uiState.isQuoteBottomSheetVisible) {
                AddQuoteBottomSheet(
                    quotes = uiState.quotes,
                    onAddClick = { onAction(CanvasMemoAction.AddQuote) },
                )
            }

            // Dialog 표시
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

            if (uiState.isQuoteDialogVisible) {
                AddQuoteDialog(
                    quoteState = uiState.quoteState,
                    pageState = uiState.pageState,
                    enabled = uiState.characterNameState.text.isNotBlank() && uiState.quoteState.text.isNotBlank(),
                    onDismiss = { onAction(CanvasMemoAction.CloseQuoteDialog) },
                    onConfirm = { /* TODO: onAction(CanvasMemoAction.AddQuote) 구현 */ }
                )
            }

            ToolExpandableButton(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = And03Padding.PADDING_XS,
                        bottom = And03Padding.PADDING_4XL + And03ComponentSize.BOTTOM_BAR_ITEM_SIZE + And03Spacing.SPACE_L
                    ),
                actions = listOf(
                    ToolAction(
                        iconRes = R.drawable.ic_add_filled,
                        contentDescription = stringResource(R.string.tool_ic_content_desc_zoom_in),
                        onClick = {
                            scale = (scale * 1.2f)
                                .coerceIn(
                                    CanvasMemoScreenValues.MIN_SCALE,
                                    CanvasMemoScreenValues.MAX_SCALE
                                )
                        }
                    ),
                    ToolAction(
                        iconRes = R.drawable.ic_remove_filled,
                        contentDescription = stringResource(R.string.tool_ic_content_desc_zoom_out),
                        onClick = {
                            scale = (scale / 1.2f)
                                .coerceIn(
                                    CanvasMemoScreenValues.MIN_SCALE,
                                    CanvasMemoScreenValues.MAX_SCALE
                                )
                        }
                    ),
                    ToolAction(
                        iconRes = R.drawable.ic_fit_screen,
                        contentDescription = stringResource(R.string.tool_ic_content_desc_fit_screen),
                        onClick = {
                            scale = 1f
                            panOffset = Offset(0f, 0f)
                        }
                    )
                )
            )

            MainBottomBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = And03Spacing.SPACE_XS),
                items = listOf(
                    MainBottomBarItem(
                        type = MainBottomBarType.NODE,
                        label = stringResource(R.string.canvas_bottom_bar_node),
                        icon = Icons.Default.PersonAdd,
                        backgroundColor = CanvasMemoColors.Node
                    ),
                    MainBottomBarItem(
                        type = MainBottomBarType.RELATION,
                        label = stringResource(R.string.canvas_bottom_bar_relation),
                        icon = Icons.Default.Link,
                        backgroundColor = CanvasMemoColors.Relation
                    ),
                    MainBottomBarItem(
                        type = MainBottomBarType.QUOTE,
                        label = stringResource(R.string.canvas_bottom_bar_quote),
                        icon = Icons.Default.FormatQuote,
                        backgroundColor = CanvasMemoColors.Quote
                    ),
                    MainBottomBarItem(
                        type = MainBottomBarType.DELETE,
                        label = stringResource(R.string.canvas_bottom_bar_delete),
                        icon = Icons.Default.Delete,
                        backgroundColor = CanvasMemoColors.Delete
                    )

                ),
                selectedType = uiState.selectedBottomBarType,
                onItemClick = { type ->
                    onAction(CanvasMemoAction.OnBottomBarClick(type))
                }
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
    val previewState = CanvasMemoUiState(
        selectedBottomBarType = MainBottomBarType.NODE
    )

    CanvasMemoScreen(
        uiState = previewState,
        onAction = {},
    )
}
