package com.boostcamp.and03.ui.screen.canvasmemo

import android.R.attr.onClick
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.screen.canvasmemo.component.AddNodeBottomSheet
import com.boostcamp.and03.ui.screen.canvasmemo.component.AddQuoteBottomSheet
import com.boostcamp.and03.ui.screen.canvasmemo.component.AddQuoteDialog
import com.boostcamp.and03.ui.screen.canvasmemo.component.AlertAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.AlertMessageCard
import com.boostcamp.and03.ui.screen.canvasmemo.component.NodeItem
import com.boostcamp.and03.ui.screen.canvasmemo.component.QuoteItem
import com.boostcamp.and03.ui.screen.canvasmemo.component.RelationEditorDialog
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolExpandableButton
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBar
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarItem
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
import com.boostcamp.and03.ui.screen.canvasmemo.model.EdgeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.RelationAddStep
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.theme.CanvasMemoColors
import com.boostcamp.and03.ui.util.collectWithLifecycle
import kotlinx.coroutines.launch

private object CanvasMemoScreenValues {
    val CANVAS_SIZE = 2000.dp
}

@Composable
fun CanvasMemoRoute(
    navigateToBack: () -> Unit,
    viewModel: CanvasMemoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.event.collectWithLifecycle { event ->
        when (event) {
            is CanvasMemoEvent.NavToBack -> navigateToBack()
        }
    }

    CanvasMemoScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CanvasMemoScreen(
    uiState: CanvasMemoUiState,
    onAction: (CanvasMemoAction) -> Unit,
) {
    var nodeSizes by remember { mutableStateOf<Map<String, IntSize>>(emptyMap()) }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    BackHandler(onBack = { onAction(CanvasMemoAction.ClickBack) })

    Scaffold(
        topBar = {
            And03AppBar(
                title = stringResource(R.string.canvas_memo_top_bar_title),
                onBackClick = { onAction(CanvasMemoAction.ClickBack) }
            ) {
                IconButton(
                    enabled = uiState.hasUnsavedChanges,
                    onClick = { onAction(CanvasMemoAction.OnClickSave) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_save_filled),
                        contentDescription = stringResource(
                            id = R.string.content_description_save_button
                        )
                    )
                }

                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_more_vert_filled),
                        contentDescription = stringResource(
                            id = R.string.content_description_more_button
                        )
                    )
                }
            }
        },
        bottomBar = {
            when {
                uiState.isBottomBarVisible -> {
                    MainBottomBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsPadding(
                                WindowInsets.safeDrawing.only(
                                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                                )
                            ),
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

                uiState.quoteToPlace != null -> {
                    AlertMessageCard(
                        message = stringResource(R.string.canvas_memo_place_item_message),
                        actions = listOf(
                            AlertAction(
                                text = stringResource(R.string.common_cancel),
                                onClick = { onAction(CanvasMemoAction.CancelPlaceItem) }
                            )
                        ),
                        modifier = Modifier
                            .padding(
                                vertical = And03Padding.PADDING_L,
                                horizontal = And03Padding.PADDING_XL
                            )
                            .windowInsetsPadding(
                                WindowInsets.safeDrawing.only(
                                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                                )
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
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(uiState.quoteToPlace) {
                            if (uiState.quoteToPlace != null) {
                                detectTapGestures { tapOffset ->
                                    onAction(CanvasMemoAction.TapCanvas(tapOffset))
                                }
                            }
                        }
                        .pointerInput(Unit) {
                            detectTransformGestures { centroid, pan, zoom, _ ->
                                onAction(
                                    CanvasMemoAction.ZoomCanvasByGesture(
                                        centroid = centroid, // 터치 포인트들의 중심점
                                        moveOffset = pan,    // 사용자가 움직인 방향과 거리
                                        zoomChange = zoom    // 확대/축소 비율
                                    )
                                )
                            }
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(CanvasMemoScreenValues.CANVAS_SIZE)
                            .graphicsLayer {
                                scaleX = uiState.zoomScale
                                scaleY = uiState.zoomScale
                                translationX = uiState.canvasViewOffset.x
                                translationY = uiState.canvasViewOffset.y
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
                                    val isDraggable =
                                        uiState.relationAddStep == RelationAddStep.NONE

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
                                        onClick = { nodeId ->
                                            onAction(CanvasMemoAction.OnNodeClick(nodeId))
                                        },
                                        onSizeChanged = { size ->
                                            nodeSizes = nodeSizes + (uiModel.node.id to size)
                                        },
                                        draggable = isDraggable,
                                        content = {
                                            NodeItem(
                                                title = uiModel.node.name,
                                                content = uiModel.node.description,
                                                isHighlighted = uiModel.isSelected
                                            )
                                        }
                                    )
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
                                        },
                                        content = {
                                            QuoteItem(
                                                quote = uiModel.node.content,
                                                page = uiModel.node.page
                                            )
                                        }
                                    )
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
                                text = "Offset: (${uiState.canvasViewOffset.x.toInt()}, ${uiState.canvasViewOffset.y.toInt()})",
                                color = And03Theme.colors.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "Scale: ${(uiState.zoomScale * 100).toInt()}%",
                                color = And03Theme.colors.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                if (uiState.isRelationDialogVisible &&
                    uiState.relationAddStep == RelationAddStep.COMPLETE
                ) {
                    val relationDialogState = uiState.relationDialogUiState

                    RelationEditorDialog(
                        relationNameState = uiState.relationNameState,
                        fromName = relationDialogState.fromName,
                        toName = relationDialogState.toName,
                        fromImageUrl = relationDialogState.fromImageUrl,
                        toImageUrl = relationDialogState.toImageUrl,
                        onDismiss = { onAction(CanvasMemoAction.CloseRelationDialog) },
                        onConfirm = {
                            onAction(
                                CanvasMemoAction.ConfirmRelation(
                                    fromId = relationDialogState.fromNodeId,
                                    toId = relationDialogState.toNodeId,
                                    name = relationDialogState.relationNameState.text.toString()
                                )
                            )
                        },
                        onFromImageClick = { /* 인물 선택 로직 */ },
                        onToImageClick = { /* 인물 선택 로직 */ }
                    )
                }

                if (uiState.relationAddStep == RelationAddStep.READY) {
                    AlertMessageCard(
                        message = "관계를 시작할 인물을 선택해 주세요.",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(
                                vertical = And03Padding.PADDING_XL,
                                horizontal = And03Padding.PADDING_L
                            )
                    )
                }

                if (uiState.relationAddStep == RelationAddStep.FROM_ONLY) {
                    AlertMessageCard(
                        message = "연결할 다른 인물을 선택해 주세요.",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(
                                vertical = And03Padding.PADDING_XL,
                                horizontal = And03Padding.PADDING_L
                            )
                    )
                }

                uiState.bottomSheetType?.let { sheetType ->
                    ModalBottomSheet(
                        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                        onDismissRequest = { onAction(CanvasMemoAction.CloseBottomSheet) },
                        sheetState = sheetState,
                        containerColor = And03Theme.colors.background
                    ) {
                        when (sheetType) {
                            CanvasMemoBottomSheetType.AddCharacter -> {
                                AddNodeBottomSheet(
                                    characters = uiState.characters,
                                    infoTitle = stringResource(R.string.add_node_bottom_sheet_info_title),
                                    infoDescription = stringResource(R.string.add_node_bottom_sheet_info_description),
                                    onSearch = { },
                                    onNewCharacterClick = { },
                                    onAddClick = {
                                        scope.launch {
                                            sheetState.hide()
                                            // TODO: onAction(CanvasMemoAction.AddNodeItem) 구현 필요
                                        }
                                    }
                                )
                            }

                            CanvasMemoBottomSheetType.AddQuote -> {
                                AddQuoteBottomSheet(
                                    quotes = uiState.quotes,
                                    onAddClick = { quote ->
                                        scope.launch {
                                            sheetState.hide()
                                            onAction(CanvasMemoAction.PrepareQuotePlacement(quote))
                                        }
                                    },
                                    onNewSentenceClick = {
                                        scope.launch {
                                            sheetState.hide()
                                            onAction(CanvasMemoAction.AddNewQuote)
                                        }
                                    },
                                    onSearch = { /* TODO: onAction(CanvasMemoAction.SearchQuote()) 구현 */ }
                                )
                            }
                        }
                    }
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
                        onDismiss = { onAction(CanvasMemoAction.CloseQuoteDialog) },
                        onConfirm = { onAction(CanvasMemoAction.SaveQuote) },
                        enabled = uiState.isQuoteSaveable && !uiState.isSaving,
                        isSaving = uiState.isSaving,
                        totalPage = uiState.totalPage
                    )
                }

                ToolExpandableButton(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = And03Padding.PADDING_XS),
                    actions = listOf(
                        ToolAction(
                            iconRes = R.drawable.ic_add_filled,
                            contentDescription = stringResource(R.string.tool_ic_content_desc_zoom_in),
                            onClick = { onAction(CanvasMemoAction.ZoomIn) }
                        ),
                        ToolAction(
                            iconRes = R.drawable.ic_remove_filled,
                            contentDescription = stringResource(R.string.tool_ic_content_desc_zoom_out),
                            onClick = { onAction(CanvasMemoAction.ZoomOut) }
                        ),
                        ToolAction(
                            iconRes = R.drawable.ic_fit_screen,
                            contentDescription = stringResource(R.string.tool_ic_content_desc_fit_screen),
                            onClick = { onAction(CanvasMemoAction.ResetZoom) }
                        )
                    )
                )

                // QuoteItem의 크기 측정을 위해 별도의 Box에서 QuoteItem 생성
                if (uiState.quoteToPlace != null && uiState.quoteItemSizePx == null) {
                    Box(
                        modifier = Modifier
                            .alpha(0f)
                    ) {
                        QuoteItem(
                            quote = uiState.quoteToPlace.content,
                            page = uiState.quoteToPlace.page,
                            onSizeChanged = { size ->
                                onAction(CanvasMemoAction.UpdateQuoteItemSize(size))
                            }
                        )
                    }
                }
            }
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
    content: @Composable BoxScope.() -> Unit,
    onClick: ((String) -> Unit)? = null,
    draggable: Boolean = true,
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
            .then(
                if (onClick != null) {
                    Modifier.pointerInput(nodeId) {
                        detectTapGestures(
                            onTap = {
                                onClick(nodeId)
                            }
                        )
                    }
                } else {
                    Modifier
                }
            )
            .then(
                if (draggable) {
                    Modifier.pointerInput(nodeId) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            onMove(dragAmount)
                        }
                    }
                } else Modifier
            )
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
    val textMeasurer = rememberTextMeasurer()

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

                val path = Path().apply {
                    moveTo(start.x, start.y)
                    lineTo(midX, start.y)
                    lineTo(midX, end.y)
                    lineTo(end.x, end.y)
                }

                drawPath(
                    path = path,
                    color = Color.Black,
                    style = Stroke(width = 4f)
                )

                val label = edge.edge.name

                if (label.isNotEmpty()) {
                    val textLayoutResult = textMeasurer.measure(label)
                    val textWidth = textLayoutResult.size.width
                    val textHeight = textLayoutResult.size.height

                    val textPos = Offset(
                        x = midX - textWidth / 2,
                        y = (start.y + end.y) / 2 - textHeight / 2
                    )

                    drawRect(
                        color = Color.White,
                        topLeft = textPos,
                        size = Size(textWidth.toFloat(), textHeight.toFloat())
                    )

                    // 텍스트 그리기
                    drawText(
                        textLayoutResult = textLayoutResult,
                        topLeft = textPos
                    )
                }
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