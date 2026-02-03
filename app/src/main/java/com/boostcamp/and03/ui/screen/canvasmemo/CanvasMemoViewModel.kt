package com.boostcamp.and03.ui.screen.canvasmemo

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.repository.bookstorage.BookStorageRepository
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.domain.model.MemoNode
import com.boostcamp.and03.domain.repository.CanvasMemoRepository
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.toUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
import com.boostcamp.and03.ui.screen.canvasmemo.model.RelationAddStep
import com.boostcamp.and03.ui.screen.canvasmemo.model.clearSelection
import com.boostcamp.and03.ui.screen.canvasmemo.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

private object CanvasZoomValues {
    const val ZOOM_STEP = 0.2f
    const val MIN_ZOOM = 0.5f
    const val MAX_ZOOM = 2.0f
    const val MAX_MOVE_RANGE = 1000f
}

@HiltViewModel
class CanvasMemoViewModel @Inject constructor(
    private val canvasMemoRepository: CanvasMemoRepository,
    private val bookStorageRepository: BookStorageRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val canvasMemoRoute = savedStateHandle.toRoute<Route.CanvasMemo>()
    private val bookId = canvasMemoRoute.bookId
    private val memoId = canvasMemoRoute.memoId
    private val userId: String = "O12OmGoVY8FPYFElNjKN"
    private val totalPage = canvasMemoRoute.totalPage

    private val _uiState = MutableStateFlow(CanvasMemoUiState())
    val uiState: StateFlow<CanvasMemoUiState> = _uiState.asStateFlow()

    private val _event: Channel<CanvasMemoEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    init {
        loadCanvasMemo(
            userId = userId,
            bookId = bookId,
            memoId = memoId
        )

        observeCharacters(
            userId = userId,
            bookId = bookId
        )

        observeQuotes(
            userId = userId,
            bookId = bookId
        )
    }

    /**
     * 현재 그래프 데이터를 가져옵니다.
     *
     */
    private fun getCurrentGraph(): MemoGraph {
        val nodes = _uiState.value.nodes.mapValues { it.value.node }
        val edges = _uiState.value.edges.map { it.edge }
        return MemoGraph(nodes, edges)
    }

    /**
     * 파이어베이스의 등장인물 데이터를 수집합니다.
     * 수정, 삭제 시 최신 상태가 반영됩니다.
     */
    private fun observeCharacters(
        userId: String,
        bookId: String
    ) {
        viewModelScope.launch {
            bookStorageRepository.getCharacters(userId, bookId).collect { result ->
                _uiState.update { state ->
                    state.copy(characters = result.map { it.toUiModel() }.toImmutableList())
                }
            }
        }
    }

    /**
     * 파이어베이스의 구절 데이터를 수집합니다.
     * 수정, 삭제 시 최신 상태가 반영됩니다.
     */
    private fun observeQuotes(
        userId: String,
        bookId: String
    ) {
        viewModelScope.launch {
            bookStorageRepository.getQuotes(userId, bookId).collect { result ->
                _uiState.update { state ->
                    state.copy(quotes = result.map { it.toUiModel() }.toImmutableList())
                }
            }
        }
    }

    /**
     * 사용자의 동작(Intent, 의도)에 따른 처리를 하는 함수입니다.
     */
    fun onAction(action: CanvasMemoAction) {
        when (action) {
            CanvasMemoAction.ClickBack -> handleClickBack()

            CanvasMemoAction.CloseBottomSheet -> handleCloseBottomSheet()

            CanvasMemoAction.CloseRelationDialog -> handleCloseRelationDialog()

            CanvasMemoAction.CloseAddCharacterDialog -> handleCloseAddCharacterDialog()

            is CanvasMemoAction.OpenRelationDialog -> handleOpenRelationDialog(action)

            CanvasMemoAction.CloseQuoteDialog -> handleCloseQuoteDialog()

            CanvasMemoAction.CloseExitConfirmationDialog -> handleCloseExitConfirmationDialog()

            CanvasMemoAction.CloseScreen -> handleCloseScreen()

            is CanvasMemoAction.PrepareQuotePlacement -> handlePrepareQuotePlacement(action.quote)

            is CanvasMemoAction.SearchQuote -> handleSearchQuote(action)

            CanvasMemoAction.AddNewQuote -> handleOpenQuoteDialog()

            CanvasMemoAction.SaveQuote -> handleSaveQuote()

            is CanvasMemoAction.MoveNode -> handleMoveNode(action)

            is CanvasMemoAction.OnBottomBarClick -> {
                handleBottomBarClick(action)
                if (action.type == MainBottomBarType.RELATION) {
                    onAction(CanvasMemoAction.HideBottomBar)
                }
            }

            CanvasMemoAction.CancelPlaceItem -> handleCancelPlaceItem()

            is CanvasMemoAction.TapCanvas -> handleTapCanvas(action.tapPositionOnScreen)

            CanvasMemoAction.HideBottomBar -> setBottomBarVisible(false)

            CanvasMemoAction.ShowBottomBar -> setBottomBarVisible(true)

            is CanvasMemoAction.OnNodeClick -> handleNodeClick(action)

            is CanvasMemoAction.ConfirmRelation -> {
                handleConnectNodes(action)
            }

            is CanvasMemoAction.OnClickSave -> {
                onSaveCanvasMemo()
            }

            is CanvasMemoAction.UpdateQuoteItemSize -> {
                handleUpdateQuoteItemSize(action)
            }
            is CanvasMemoAction.PrepareNodePlacement ->
                handlePrepareNodePlacement(action.character)

            is CanvasMemoAction.AddNodeAtPosition ->
                handleAddNodeAtPosition(action.positionOnScreen)

            is CanvasMemoAction.UpdateNodeItemSize -> {
                _uiState.update { it.copy(nodeItemSizePx = action.size) }
            }




            is CanvasMemoAction.ZoomCanvasByGesture -> {
                handleZoomCanvasByGesture(
                    action.centroid,
                    action.moveOffset,
                    action.zoomChange
                )
            }

            CanvasMemoAction.ZoomIn -> handleZoomIn()

            CanvasMemoAction.ZoomOut -> handleZoomOut()

            CanvasMemoAction.ResetZoom -> handleResetZoom()
        }
    }

    /**
     * 저장되지 않은 요소가 있는지에 대한 여부에 따라
     * 확인용 다이얼로그가 표시되거나 화면을 이동합니다.
     */
    private fun handleClickBack() {
        if (_uiState.value.hasUnsavedChanges) {
            _uiState.update { it.copy(isExitConfirmationDialogVisible = true) }
        } else {
            _event.trySend(CanvasMemoEvent.NavToBack)
        }
    }

    /**
     * 바텀 시트를 가립니다.
     */
    private fun handleCloseBottomSheet() {
        _uiState.update { it.copy(bottomSheetType = null) }
    }

    /**
     * 관계 추가 다이얼로그를 닫습니다.
     */
    private fun handleCloseRelationDialog() {
        _uiState.update {
            it.copy(
                nodes = it.nodes.mapValues { (_, uiModel) ->
                    if (uiModel.isSelected) {
                        uiModel.clearSelection()
                    } else {
                        uiModel
                    }
                },
                isRelationDialogVisible = false,
                relationSelection = RelationSelection.empty(),
                relationNameState = TextFieldState(),
                isBottomBarVisible = true,
                relationAddStep = RelationAddStep.NONE,
            )
        }
    }

    /**
     * 관계 추가 다이얼로그를 엽니다.
     */
    private fun handleOpenRelationDialog(action: CanvasMemoAction.OpenRelationDialog) {
        _uiState.update {
            it.copy(
                isRelationDialogVisible = true,
                relationSelection = RelationSelection(
                    fromNodeId = action.fromNodeId,
                    toNodeId = action.toNodeId
                )
            )
        }
    }

    /**
     * 등장인물 추가 다이얼로그를 닫습니다.
     */
    private fun handleCloseAddCharacterDialog() {
        _uiState.update {
            it.copy(
                isAddCharacterDialogVisible = false,
                characterNameState = TextFieldState(),
                characterDescState = TextFieldState()
            )
        }
    }

    /**
     * 구절 추가 다이얼로그를 닫습니다.
     */
    private fun handleCloseQuoteDialog() {
        _uiState.update {
            it.copy(
                isQuoteDialogVisible = false,
                quoteState = TextFieldState(),
                pageState = TextFieldState()
            )
        }
    }

    /**
     * 나가기 확인 다이얼로그를 닫고, 캔버스 메모 화면에 잔류합니다.
     */
    private fun handleCloseExitConfirmationDialog() {
        _uiState.update { it.copy(isExitConfirmationDialogVisible = false) }
    }

    /**
     * 나가기 확인 다이얼로그를 닫고, 이전 화면으로 이동합니다.
     */
    private fun handleCloseScreen() {
        _uiState.update {
            it.copy(
                isExitConfirmationDialogVisible = false,
                hasUnsavedChanges = false
            )
        }
        _event.trySend(CanvasMemoEvent.NavToBack)
    }

    /**
     * 캔버스에 추가할 구절 데이터를 uiState에 저장합니다.
     * 바텀 시트와 바텀 바를 숨기고 다음 행동을 알려주는 AlertMessageCard를 표시합니다.
     */
    private fun handlePrepareQuotePlacement(quote: QuoteUiModel) {
        _uiState.update {
            it.copy(
                quoteToPlace = quote,
                bottomSheetType = null,
                isBottomBarVisible = false,
                quoteItemSizePx = null
            )
        }
    }

    /**
     * 구절 바텀 시트에서 검색어를 입력하여 원하는 구절을 찾습니다.
     */
    private fun handleSearchQuote(action: CanvasMemoAction.SearchQuote) {
        // TODO: 구절 검색 동작 연동
    }

    /**
     * 새로운 구절 추가 다이얼로그를 엽니다.
     */
    private fun handleOpenQuoteDialog() {
        _uiState.update {
            it.copy(
                bottomSheetType = null,
                isQuoteDialogVisible = true,
                quoteState = TextFieldState(),
                pageState = TextFieldState()
            )
        }
    }

    /**
     * 캔버스에 추가할 구절 데이터를 uiState에 저장합니다.
     * 바텀 시트와 바텀 바를 숨기고 다음 행동을 알려주는 AlertMessageCard를 표시합니다.
     */
    private fun handleSaveQuote() {
        if (_uiState.value.isSaving || !_uiState.value.isQuoteSaveable) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            try {
                val quote = _uiState.value.quoteState.text.toString()
                val page = _uiState.value.pageState.text.toString().toInt()
                val newQuoteUiModel = QuoteUiModel(
                    content = quote,
                    page = page
                )

                bookStorageRepository.addQuote(
                    userId = userId,
                    bookId = bookId,
                    quote = newQuoteUiModel
                )

                _uiState.update {
                    it.copy(
                        isQuoteDialogVisible = false,
                        bottomSheetType = CanvasMemoBottomSheetType.AddQuote,
                        quoteState = TextFieldState(),
                        pageState = TextFieldState(),
                        hasUnsavedChanges = false
                    )
                }
            } catch (e: Exception) {
                // TODO: 오류 메시지 UI 표시 구현
                _uiState.update { it.copy(hasUnsavedChanges = true) }
            }
        }
    }

    /**
     * 노드를 움직이고, 해당 노드를 uiState의 노드 리스트 데이터에 반영합니다.
     */
    private fun handleMoveNode(action: CanvasMemoAction.MoveNode) {
        val graph = getCurrentGraph()
        val updatedGraph = graph.moveNode(action.nodeId, action.newOffset)

        val movedNode = updatedGraph.nodes[action.nodeId] ?: return

        _uiState.update {
            it.copy(
                nodes = it.nodes + (action.nodeId to movedNode.toUiModel()),
                hasUnsavedChanges = true
            )
        }
    }

    /**
     * 노드를 연결하고, 해당 엣지를 uiState의 엣지 리스트 데이터에 반영합니다.
     */
    private fun handleConnectNodes(action: CanvasMemoAction.ConfirmRelation) {
        val graph = getCurrentGraph()
        val updatedGraph = graph.connectNode(action.fromId, action.toId, action.name)

        _uiState.update {
            it.copy(
                edges = updatedGraph.edges.map { it.toUiModel() },
                hasUnsavedChanges = true
            )
        }

        resetRelation()
    }

    /**
     * 바텀 바의 버튼을 클릭하여, 버튼에 해당하는 동작을 처리합니다.
     * 등장인물과 구절의 경우 바텀 시트 상태를 활성화합니다.
     */
    private fun handleBottomBarClick(action: CanvasMemoAction.OnBottomBarClick) {
        val sheetType = when (action.type) {
            MainBottomBarType.NODE -> CanvasMemoBottomSheetType.AddCharacter
            MainBottomBarType.QUOTE -> CanvasMemoBottomSheetType.AddQuote
            else -> null
        }

        when (action.type) {
            MainBottomBarType.RELATION -> enterRelationMode()
            else -> {
                _uiState.update {
                    it.copy(
                        selectedBottomBarType = action.type,
                        bottomSheetType = sheetType
                    )
                }
            }
        }

        _uiState.update {
            it.copy(
                selectedBottomBarType = action.type,
                bottomSheetType = sheetType
            )
        }
    }

    /**
     * 구절의 아이템을 배치하지 않고 취소합니다.
     * 바텀 바를 다시 표시합니다.
     */
    private fun handleCancelPlaceItem() {
        _uiState.update {
            it.copy(
                quoteToPlace = null,
                isBottomBarVisible = true
            )
        }
    }

    /**
     * 구절 아이템을 배치, 렌더링합니다.
     *
     * 배치할 QuoteUiModel이 준비되었는지와 해당 데이터를 토대로 그려진 컴포저블의 크기가 계산되었는지 검사합니다.
     * 계산되었다면 해당 아이템의 너비와 높이의 절반 값을 구합니다.
     *
     * 확대/축소 비율과 캔버스의 이동 오프셋을 고려해
     * 화면 좌표(tapPositionOnScreen)를 캔버스 좌표계로 변환합니다.
     * 이후 바텀바를 다시 표시합니다.
     */
    private fun handleTapCanvas(tapPositionOnScreen: Offset) {
        val quote = _uiState.value.quoteToPlace ?: return
        val sizeDp = _uiState.value.quoteItemSizePx ?: return

        val centerItemPosition = Offset(
            x = sizeDp.width / 2f,
            y = sizeDp.height / 2f
        )

        val zoomScale = _uiState.value.zoomScale
        val canvasViewOffset = _uiState.value.canvasViewOffset

        val canvasPosition = Offset(
            x = (tapPositionOnScreen.x - canvasViewOffset.x) / zoomScale - centerItemPosition.x,
            y = (tapPositionOnScreen.y - canvasViewOffset.y) / zoomScale - centerItemPosition.y
        )

        val newQuote = MemoNode.QuoteNode(
            id = UUID.randomUUID().toString(),
            content = quote.content,
            page = quote.page,
            offset = canvasPosition
        )

        _uiState.update {
            it.copy(
                nodes = it.nodes + (newQuote.id to newQuote.toUiModel()),
                quoteToPlace = null,
                quoteItemSizePx = null,
                isBottomBarVisible = true,
                hasUnsavedChanges = true
            )
        }
    }

    /**
     * 바텀바의 표시 여부를 설정합니다.
     */
    private fun setBottomBarVisible(visible: Boolean) {
        _uiState.update {
            it.copy(isBottomBarVisible = visible)
        }
    }

    /**
     * 관계 추가 준비를 위한 _uiState의 상태를 업데이트합니다.
     */
    private fun enterRelationMode() {
        _uiState.update {
            it.copy(
                selectedBottomBarType = MainBottomBarType.RELATION,
                relationSelection = RelationSelection.empty(),
                relationAddStep = RelationAddStep.READY,
                isBottomBarVisible = false
            )
        }
    }

    /**
     * 관계를 추가할 때 클릭한 노드에 대한 처리를 진행합니다.
     */
    private fun handleNodeClick(action: CanvasMemoAction.OnNodeClick) {
        when (_uiState.value.relationAddStep) {
            RelationAddStep.READY ->
                selectFrom(action.nodeId)

            RelationAddStep.FROM_ONLY ->
                selectToOrCancel(action.nodeId)

            RelationAddStep.COMPLETE ->
                handleCompleteStateClick(action.nodeId)

            RelationAddStep.NONE -> Unit
        }
    }

    /**
     * 관계를 추가할 때 첫 번째로 클릭한 노드의 id를 fromId으로 설정합니다.
     */
    private fun selectFrom(nodeId: String) {
        val selection = RelationSelection(fromNodeId = nodeId, toNodeId = null)

        _uiState.update {
            it.copy(
                relationSelection = selection,
                relationAddStep = RelationAddStep.FROM_ONLY
            )
        }
        updateNodeSelection(selection)
    }

    /**
     * 관계를 추가할 때 두 번째로 클릭한 노드의 id 값을 확인,
     * 같은 id면 취소(자기 자신을 관계로 연결할 수 없음), 다른 id면 toId로 설정합니다.
     * toId까지 설정되었을 경우 관계 추가 다이얼로그를 엽니다.
     */
    private fun selectToOrCancel(nodeId: String) {
        val selection = _uiState.value.relationSelection

        // 같은 노드 다시 클릭 → from 취소
        if (selection.fromNodeId == nodeId) {
            resetRelation()
            return
        }

        val updated = selection.copy(toNodeId = nodeId)

        _uiState.update {
            it.copy(
                relationSelection = updated,
                relationAddStep = RelationAddStep.COMPLETE,
                isRelationDialogVisible = true,
                relationNameState = TextFieldState()
            )
        }
        updateNodeSelection(updated)
    }

    /**
     * 관계를 추가할 때 완료 버튼을 클릭한 경우, 관계 데이터를 추가하고 업데이트합니다.
     * 관계 추가 다이얼로그를 닫습니다.
     */
    private fun handleCompleteStateClick(nodeId: String) {
        val selection = _uiState.value.relationSelection

        val updated = when (nodeId) {
            selection.toNodeId ->
                selection.copy(toNodeId = null)

            selection.fromNodeId ->
                selection.copy(
                    fromNodeId = selection.toNodeId,
                    toNodeId = null
                )

            else -> selection
        }

        _uiState.update {
            it.copy(
                relationSelection = updated,
                relationAddStep = RelationAddStep.FROM_ONLY,
                isRelationDialogVisible = false
            )
        }
        updateNodeSelection(updated)
    }

    /**
     * 관계 선택 단계 - 관계 선택 관련 필드 값을 초기화합니다.
     * 바텀 시트를 숨기고 바텀 바를 드러냅니다.
     */
    private fun resetRelation() {
        _uiState.update {
            it.copy(
                relationSelection = RelationSelection.empty(),
                relationAddStep = RelationAddStep.NONE,
                isRelationDialogVisible = false,
                isBottomBarVisible = true
            )
        }
        updateNodeSelection(RelationSelection.empty())
    }

//    private fun handleNodeClick(action: CanvasMemoAction.OnNodeClick) {
//        val selection = _uiState.value.relationSelection
//
//        when {
//            // from이 아직 없을 때
//            selection.fromNodeId == null -> {
//                val updated = selection.copy(fromNodeId = action.nodeId)
//
//                _uiState.update {
//                    it.copy(relationSelection = updated)
//                }
//                updateNodeSelection(updated)
//            }
//
//            // from은 있는데 같은 노드를 다시 클릭한 경우
//            action.nodeId == selection.fromNodeId && selection.toNodeId == null -> {
//                val updated = selection.copy(fromNodeId = null)
//
//                _uiState.update {
//                    it.copy(relationSelection = updated)
//                }
//                updateNodeSelection(updated)
//            }
//
//            // to가 아직 없을 때 + from과 다른 노드
//            selection.toNodeId == null -> {
//                val updated = selection.copy(toNodeId = action.nodeId)
//
//                _uiState.update {
//                    it.copy(
//                        relationSelection = updated,
//                        isRelationDialogVisible = updated.isComplete,
//                        relationNameState = TextFieldState()
//                    )
//                }
//
//                updateNodeSelection(updated)
//            }
//
//            // 둘 다 선택되었고 to와 같은 노드를 클릭한 경우
//            action.nodeId == selection.toNodeId -> {
//                val updated = selection.copy(toNodeId = null)
//
//                _uiState.update {
//                    it.copy(
//                        relationSelection = updated,
//                    )
//                }
//                updateNodeSelection(updated)
//            }
//
//            // 둘 다 선택되었고 from과 같은 노드를 클릭한 경우
//            action.nodeId == selection.fromNodeId -> {
//                val updated = selection.copy(fromNodeId = selection.toNodeId, toNodeId = null)
//
//                _uiState.update {
//                    it.copy(
//                        relationSelection = updated,
//                    )
//                }
//                updateNodeSelection(updated)
//            }
//
//        }
//        Log.d("CanvasMemoViewModel", "handleNodeClick: ${_uiState.value.relationSelection}")
//    }

    /**
     * 관계 추가 시 지정했던 Id 데이터를 토대로 MemoNodeUiModel을 생성합니다.
     */
    private fun updateNodeSelection(selection: RelationSelection) {
        _uiState.update { state ->
            state.copy(
                nodes = state.nodes.mapValues { (_, uiModel) ->
                    val isSelected =
                        selection.fromNodeId == uiModel.node.id ||
                                selection.toNodeId == uiModel.node.id

                    uiModel.node.toUiModel(
                        isSelected = isSelected,
                        isDragging = uiModel.isDragging,
                        size = uiModel.size
                    )
                }
            )
        }
    }

    /**
     * 캔버스에 있는 아이템 데이터들을 저장합니다.
     */
    private fun onSaveCanvasMemo() {
        viewModelScope.launch {
            try {
                val graph = getCurrentGraph()
                canvasMemoRepository.saveCanvasMemo(
                    userId = userId,
                    bookId = bookId,
                    memoId = memoId,
                    graph = graph
                )
                _uiState.update { it.copy(hasUnsavedChanges = false) }
            } catch (e: Exception) {
                // TODO: 오류 표시 UI 구현
                _uiState.update { it.copy(hasUnsavedChanges = true) }
            }
        }
    }

    private fun loadCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String
    ) {
        viewModelScope.launch {
            canvasMemoRepository.loadCanvasMemoDetail(
                userId = userId,
                bookId = bookId,
                memoId = memoId,
            ).collect { graph ->
                _uiState.update {
                    it.copy(
                        nodes = graph.nodes.mapValues { it.value.toUiModel() },
                        edges = graph.edges.map { it.toUiModel() },
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun deleteCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        nodeIds: List<String>
    ) {
        viewModelScope.launch {
            try {
                canvasMemoRepository.removeNode(
                    userId = userId,
                    bookId = bookId,
                    memoId = memoId,
                    nodeIds = nodeIds
                )
            } catch (e: Exception) {
                Log.d("CanvasMemoViewModel", "onSaveCanvasMemo: $e")
            }
        }
    }

    /**
     * `QuoteItem`의 onGloballyPositioned을 실행,
     * 해당 컴포저블의 너비와 높이 값 IntSize를 가져옵니다.
     */
    private fun handleUpdateQuoteItemSize(action: CanvasMemoAction.UpdateQuoteItemSize) {
        _uiState.update { it.copy(quoteItemSizePx = action.size) }
    }

    /**
     * 두 손가락의 중심점을 기준으로 캔버스를 확대 또는 축소합니다.
     * (캔버스 위치 - 두 손가락의 중심점) * 확대 or 축소 비율 + 두 손가락의 중심점 + 제스처 이동량
     */
    private fun handleZoomCanvasByGesture(
        centroid: Offset,
        moveOffset: Offset,
        zoomChange: Float
    ) {
        _uiState.update {
            val oldZoomScale = it.zoomScale
            val newZoomScale = (oldZoomScale * zoomChange)
                .coerceIn(
                    minimumValue = CanvasZoomValues.MIN_ZOOM,
                    maximumValue = CanvasZoomValues.MAX_ZOOM
                )

            val scaleRatio = newZoomScale / oldZoomScale

            val newCanvasViewOffset = clampCanvasViewOffset(
                (it.canvasViewOffset - centroid) * scaleRatio + centroid + moveOffset
            )

            it.copy(
                zoomScale = newZoomScale,
                canvasViewOffset = newCanvasViewOffset
            )
        }
    }

    /**
     * 캔버스를 20% 확대합니다.
     */
    private fun handleZoomIn() {
        _uiState.update {
            it.copy(
                zoomScale = (it.zoomScale + CanvasZoomValues.ZOOM_STEP)
                    .coerceIn(
                        CanvasZoomValues.MIN_ZOOM,
                        CanvasZoomValues.MAX_ZOOM
                    )
            )
        }
    }

    /**
     * 캔버스를 20% 축소합니다.
     */
    private fun handleZoomOut() {
        _uiState.update {
            it.copy(
                zoomScale = (it.zoomScale - CanvasZoomValues.ZOOM_STEP)
                    .coerceIn(
                        CanvasZoomValues.MIN_ZOOM,
                        CanvasZoomValues.MAX_ZOOM
                    )
            )
        }
    }

    /**
     * 캔버스의 확대 축소 배율을 초기화합니다.
     */
    private fun handleResetZoom() {
        _uiState.update { it.copy(zoomScale = 1f) }
    }

    private fun clampCanvasViewOffset(offset: Offset): Offset {
        val max = CanvasZoomValues.MAX_MOVE_RANGE
        val min = -CanvasZoomValues.MAX_MOVE_RANGE

        return Offset(
            x = offset.x.coerceIn(min, max),
            y = offset.y.coerceIn(min, max)
        )
    }

    private fun handlePrepareNodePlacement(character: CharacterUiModel) {
        _uiState.update {
            it.copy(
                nodeToPlace = character,
                bottomSheetType = null,
                isBottomBarVisible = false
            )
        }
    }
    private fun handleAddNodeAtPosition(tapPositionOnScreen: Offset) {
        val character = _uiState.value.nodeToPlace ?: return
        val size = _uiState.value.nodeItemSizePx ?: return

        val centerOffset = Offset(
            size.width / 2f,
            size.height / 2f
        )

        val zoomScale = _uiState.value.zoomScale
        val canvasViewOffset = _uiState.value.canvasViewOffset

        val canvasPosition = Offset(
            x = (tapPositionOnScreen.x - canvasViewOffset.x) / zoomScale - centerOffset.x,
            y = (tapPositionOnScreen.y - canvasViewOffset.y) / zoomScale - centerOffset.y
        )

        val newNode = MemoNode.CharacterNode(
            id = UUID.randomUUID().toString(),
            name = character.name,
            description = character.description,
            offset = canvasPosition,
            imageUrl = character.imageUri,
            iconColor = character.profileColor.toString(),
            profileType = character.profileType,
        )

        _uiState.update {
            it.copy(
                nodes = it.nodes + (newNode.id to newNode.toUiModel()),
                nodeToPlace = null,
                nodeItemSizePx = null,
                isBottomBarVisible = true,
                hasUnsavedChanges = true
            )
        }
    }




}