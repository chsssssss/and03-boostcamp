package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
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
import com.boostcamp.and03.ui.component.And03Dialog
import com.boostcamp.and03.ui.component.DialogDismissAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.AddNodeBottomSheet
import com.boostcamp.and03.ui.screen.canvasmemo.component.AddQuoteBottomSheet
import com.boostcamp.and03.ui.screen.canvasmemo.component.AddQuoteDialog
import com.boostcamp.and03.ui.screen.canvasmemo.component.AlertAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.AlertMessageCard
import com.boostcamp.and03.ui.screen.canvasmemo.component.Arrows
import com.boostcamp.and03.ui.screen.canvasmemo.component.DraggableCanvasItem
import com.boostcamp.and03.ui.screen.canvasmemo.component.NodeItem
import com.boostcamp.and03.ui.screen.canvasmemo.component.QuoteItem
import com.boostcamp.and03.ui.screen.canvasmemo.component.RelationEditorDialog
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolExpandableButton
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBar
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.RelationAddStep
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Theme
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

    BackHandler {
        when {
            uiState.isExitConfirmationDialogVisible -> onAction(CanvasMemoAction.CloseExitConfirmationDialog)

            uiState.isSureDeleteDialogVisible -> onAction(CanvasMemoAction.CloseSureDeleteDialog)

            uiState.nodeToPlace != null -> onAction(CanvasMemoAction.CancelPlaceItem)

            uiState.relationAddStep != RelationAddStep.NONE -> onAction(CanvasMemoAction.CancelRelationStep)

            uiState.quoteToPlace != null -> onAction(CanvasMemoAction.CancelPlaceItem)

            uiState.isDeleteMode -> onAction(CanvasMemoAction.CancelDeleteMode)

            else -> onAction(CanvasMemoAction.ClickBack)
        }
    }

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
                        contentDescription = stringResource(id = R.string.content_description_save_button)
                    )
                }

                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_more_vert_filled),
                        contentDescription = stringResource(id = R.string.content_description_more_button)
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
                        selectedType = uiState.selectedBottomBarType,
                        onItemClick = { type ->
                            onAction(CanvasMemoAction.OnBottomBarClick(type))
                        }
                    )
                }

                uiState.quoteToPlace != null || uiState.nodeToPlace != null -> {
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

                uiState.isDeleteMode -> {
                    AlertMessageCard(
                        message = stringResource(R.string.canvas_memo_sure_select_delete_item),
                        actions = listOf(
                            AlertAction(
                                text = stringResource(R.string.common_cancel),
                                onClick = { onAction(CanvasMemoAction.CancelDeleteMode) }
                            ),
                            AlertAction(
                                text = stringResource(R.string.common_delete),
                                onClick = { onAction(CanvasMemoAction.OpenSureDeleteDialog) }
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
                        .pointerInput(
                            uiState.quoteToPlace,
                            uiState.nodeToPlace
                        ) {
                            if (uiState.quoteToPlace != null || uiState.nodeToPlace != null) {
                                detectTapGestures { tapOffset ->
                                    uiState.quoteToPlace?.let {
                                        onAction(CanvasMemoAction.AddQuoteAtPosition(tapOffset))
                                    } ?: uiState.nodeToPlace?.let {
                                        onAction(CanvasMemoAction.AddNodeAtPosition(tapOffset))
                                    }
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
                            val isDraggable =
                                uiState.relationAddStep == RelationAddStep.NONE &&
                                        !uiState.isDeleteMode

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
                                        onClick = { nodeId ->
                                            if (uiState.isDeleteMode) {
                                                onAction(CanvasMemoAction.SelectDeleteItem(nodeId))
                                            } else {
                                                onAction(CanvasMemoAction.OnNodeClick(nodeId))
                                            }
                                        },
                                        onSizeChanged = { size ->
                                            nodeSizes = nodeSizes + (uiModel.node.id to size)
                                        },
                                        draggable = isDraggable,
                                        content = {
                                            NodeItem(
                                                title = uiModel.node.name,
                                                content = uiModel.node.description,
                                                isHighlighted = if (uiState.isDeleteMode) {
                                                    uiModel.node.id in uiState.selectedDeleteItemIds
                                                } else {
                                                    uiModel.isSelected
                                                }
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
                                        onClick = { nodeId ->
                                            if (uiState.isDeleteMode) {
                                                onAction(CanvasMemoAction.SelectDeleteItem(nodeId))
                                            } else {
                                                onAction(CanvasMemoAction.OnNodeClick(nodeId))
                                            }
                                        },
                                        onSizeChanged = { size ->
                                            nodeSizes = nodeSizes + (uiModel.node.id to size)
                                        },
                                        draggable = isDraggable,
                                        content = {
                                            QuoteItem(
                                                quote = uiModel.node.content,
                                                page = uiModel.node.page,
                                                isHighlighted = if (uiState.isDeleteMode) {
                                                    uiModel.node.id in uiState.selectedDeleteItemIds
                                                } else {
                                                    uiModel.isSelected
                                                }
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

                if (uiState.isExitConfirmationDialogVisible && uiState.hasUnsavedChanges) {
                    And03Dialog(
                        iconResId = R.drawable.ic_warning_filled,
                        iconColor = And03Theme.colors.error,
                        iconContentDescription = stringResource(id = R.string.content_description_caution),
                        title = stringResource(id = R.string.canvas_memo_exit_confirmation_dialog_title),
                        dismissText = stringResource(id = R.string.canvas_memo_exit_confirmation_dialog_dismiss_text),
                        confirmText = stringResource(id = R.string.canvas_memo_exit_confirmation_dialog_confirm_text),
                        onDismiss = { onAction(CanvasMemoAction.CloseScreen) },
                        onConfirm = { onAction(CanvasMemoAction.CloseExitConfirmationDialog) },
                        description = stringResource(id = R.string.canvas_memo_exit_confirmation_dialog_description),
                        dismissAction = DialogDismissAction.Confirm
                    )
                }

                if (uiState.isSureDeleteDialogVisible) {
                    And03Dialog(
                        iconResId = R.drawable.ic_delete_outlined,
                        iconColor = And03Theme.colors.error,
                        iconContentDescription = stringResource(id = R.string.content_description_delete),
                        title = stringResource(id = R.string.canvas_memo_sure_delete_dialog_title),
                        dismissText = stringResource(id = R.string.common_cancel),
                        confirmText = stringResource(id = R.string.common_delete),
                        onDismiss = { onAction(CanvasMemoAction.CloseSureDeleteDialog) },
                        onConfirm = { onAction(CanvasMemoAction.DeleteSelectedItems(uiState.selectedDeleteItemIds)) },
                        dismissAction = DialogDismissAction.Confirm
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
                                    onAddClick = { character ->
                                        if (character == null) return@AddNodeBottomSheet

                                        scope.launch {
                                            sheetState.hide()
                                            onAction(
                                                CanvasMemoAction.PrepareNodePlacement(character)
                                            )
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
                if (uiState.nodeToPlace != null && uiState.nodeItemSizePx == null) {
                    Box(
                        modifier = Modifier.alpha(0f)
                    ) {
                        NodeItem(
                            title = uiState.nodeToPlace.name,
                            content = uiState.nodeToPlace.description,
                            isHighlighted = false,
                            modifier = Modifier.onGloballyPositioned { coords ->
                                onAction(
                                    CanvasMemoAction.UpdateNodeItemSize(coords.size)
                                )
                            }
                        )
                    }
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