package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.repository.bookstorage.BookStorageRepository
import com.boostcamp.and03.domain.factory.MemoGraphFactory
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.domain.repository.CanvasMemoRepository
import com.boostcamp.and03.domain.model.MemoNode
import com.boostcamp.and03.ui.navigation.Route
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
import javax.inject.Inject

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
        createInitialState()

        observeCharacters(
            userId = userId,
            bookId = bookId
        )

        observeQuotes(
            userId = userId,
            bookId = bookId
        )
    }

    private fun createInitialState() {
        val sampleGraph = MemoGraphFactory.createSample()

        _uiState.update {
            it.copy(
                nodes = sampleGraph.nodes.mapValues { it.value.toUiModel() },
                edges = sampleGraph.edges.map { it.toUiModel() },
                totalPage = totalPage
            )
        }
    }

    private fun getCurrentGraph(): MemoGraph {
        val nodes = _uiState.value.nodes.mapValues { it.value.node }
        val edges = _uiState.value.edges.map { it.edge }
        return MemoGraph(nodes, edges)
    }

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

    fun onAction(action: CanvasMemoAction) {
        when (action) {
            CanvasMemoAction.ClickBack -> handleClickBack()

            CanvasMemoAction.CloseBottomSheet -> handleCloseBottomSheet()

            CanvasMemoAction.CloseRelationDialog -> handleCloseRelationDialog()

            CanvasMemoAction.CloseAddCharacterDialog -> handleCloseAddCharacterDialog()

            is CanvasMemoAction.OpenRelationDialog -> handleOpenRelationDialog(action)

            CanvasMemoAction.CloseQuoteDialog -> handleCloseQuoteDialog()

            is CanvasMemoAction.PrepareQuotePlacement -> handlePrepareQuotePlacement(action.quote)

            is CanvasMemoAction.SearchQuote -> handleSearchQuote(action)

            CanvasMemoAction.AddNewQuote -> handleAddNewQuote()

            CanvasMemoAction.SaveQuote -> handleSaveQuote()

            is CanvasMemoAction.MoveNode -> handleMoveNode(action)

            is CanvasMemoAction.OnBottomBarClick -> {
                handleBottomBarClick(action)
                if (action.type == MainBottomBarType.RELATION) {
                    onAction(CanvasMemoAction.HideBottomBar)
                }
            }

            is CanvasMemoAction.ConnectNodes -> handleConnectNodes(action)

            is CanvasMemoAction.OnBottomBarClick -> handleBottomBarClick(action)

            CanvasMemoAction.CancelPlaceItem -> handleCancelPlaceItem()

            is CanvasMemoAction.TapCanvas -> handleTapCanvas(action.tapPositionOnScreen)

            CanvasMemoAction.HideBottomBar -> setBottomBarVisible(false)

            CanvasMemoAction.ShowBottomBar -> setBottomBarVisible(true)

            is CanvasMemoAction.OnNodeClick -> handleNodeClick(action)

            is CanvasMemoAction.ConfirmRelation -> {
                handleConnectNodes(action)
            }

            is CanvasMemoAction.onClickSave -> {
                onSaveCanvasMemo(action.userId, action.bookId, action.memoId)
            }
        }
    }

    private fun handleClickBack() {
        _event.trySend(CanvasMemoEvent.NavToBack)
    }

    private fun handleCloseBottomSheet() {
        _uiState.update { it.copy(bottomSheetType = null) }
    }

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

    private fun handleCloseAddCharacterDialog() {
        _uiState.update {
            it.copy(
                isAddCharacterDialogVisible = false,
                characterNameState = TextFieldState(),
                characterDescState = TextFieldState()
            )
        }
    }

    private fun handleCloseQuoteDialog() {
        _uiState.update {
            it.copy(
                isQuoteDialogVisible = false,
                quoteState = TextFieldState(),
                pageState = TextFieldState()
            )
        }
    }

    private fun handlePrepareQuotePlacement(quote: QuoteUiModel) {
        _uiState.update {
            it.copy(
                quoteToPlace = quote,
                bottomSheetType = null,
                isBottomBarVisible = false,
            )
        }
    }

    private fun handleSearchQuote(action: CanvasMemoAction.SearchQuote) {
        // TODO: 구절 검색 동작 연동
    }

    private fun handleAddNewQuote() {
        _uiState.update {
            it.copy(
                bottomSheetType = null,
                isQuoteDialogVisible = true,
                quoteState = TextFieldState(),
                pageState = TextFieldState()
            )
        }
    }

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

                _uiState.update { it.copy(isQuoteDialogVisible = false) }
            } catch (e: Exception) {
                // TODO: 오류 메시지 UI 표시 구현
            } finally {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun placeQuoteItem() {

    }

    private fun handleMoveNode(action: CanvasMemoAction.MoveNode) {
        val graph = getCurrentGraph()
        val updatedGraph = graph.moveNode(action.nodeId, action.newOffset)

        val movedNode = updatedGraph.nodes[action.nodeId] ?: return

        _uiState.update {
            it.copy(
                nodes = it.nodes + (action.nodeId to movedNode.toUiModel())
            )
        }
    }

    private fun handleConnectNodes(action: CanvasMemoAction.ConfirmRelation) {
        val graph = getCurrentGraph()
        val updatedGraph = graph.connectNode(action.fromId, action.toId, action.name)

        _uiState.update {
            it.copy(
                edges = updatedGraph.edges.map { it.toUiModel() },
            )
        }

        resetRelation()
    }

    private fun handleBottomBarClick(action: CanvasMemoAction.OnBottomBarClick) {
        when (action.type) {
            MainBottomBarType.RELATION -> enterRelationMode()
            else -> {
                _uiState.update {
                    it.copy(
                        selectedBottomBarType = action.type
                    )
                }
            }
        }

        val sheetType = when (action.type) {
            MainBottomBarType.NODE -> CanvasMemoBottomSheetType.AddCharacter
            MainBottomBarType.QUOTE -> CanvasMemoBottomSheetType.AddQuote
            else -> null
        }

        _uiState.update {
            it.copy(
                selectedBottomBarType = action.type,
                bottomSheetType = sheetType
            )
        }
    }

    private fun handleCancelPlaceItem() {
        _uiState.update {
            it.copy(
                quoteToPlace = null,
                isBottomBarVisible = true
            )
        }
    }

    private fun handleTapCanvas(tapPositionOnScreen: Offset) {
        val quote = _uiState.value.quoteToPlace ?: return

        val zoomScale = _uiState.value.zoomScale
        val canvasViewOffset = _uiState.value.canvasViewOffset

        val canvasPosition = Offset(
            x = (tapPositionOnScreen.x - canvasViewOffset.x) / zoomScale,
            y = (tapPositionOnScreen.y - canvasViewOffset.y) / zoomScale
        )

        val newQuote = MemoNode.QuoteNode(
            id = quote.id,
            content = quote.content,
            page = quote.page,
            offset = canvasPosition
        )

        _uiState.update {
            it.copy(
                nodes = it.nodes + (newQuote.id to newQuote.toUiModel()),
                quoteToPlace = null,
                isBottomBarVisible = true
            )
        }
    }

    private fun setBottomBarVisible(visible: Boolean) {
        _uiState.update {
            it.copy(isBottomBarVisible = visible)
        }
    }

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

    private fun onSaveCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String
    ) {
        viewModelScope.launch {
            try {
                val graph = getCurrentGraph()
                canvasMemoRepository.addCanvasMemo(
                    userId = userId,
                    bookId = bookId,
                    memoId = memoId,
                    graph = graph
                )
            }
            catch (e: Exception) {

            }
        }
    }
}