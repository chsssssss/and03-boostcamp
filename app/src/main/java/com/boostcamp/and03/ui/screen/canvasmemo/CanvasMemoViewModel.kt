package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.repository.bookstorage.BookStorageRepository
import com.boostcamp.and03.domain.editor.CanvasMemoEditor
import com.boostcamp.and03.domain.factory.MemoGraphFactory
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.domain.model.MemoNode
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.toUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
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
    private val bookStorageRepository: BookStorageRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val canvasMemoRoute = savedStateHandle.toRoute< Route.CanvasMemo>()
    private val bookId = canvasMemoRoute.bookId
    private val memoId = canvasMemoRoute.memoId
    private val totalPage = canvasMemoRoute.totalPage

    private val _uiState = MutableStateFlow(CanvasMemoUiState())
    val uiState: StateFlow<CanvasMemoUiState> = _uiState.asStateFlow()

    private val _event: Channel<CanvasMemoEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    private val userId: String = "O12OmGoVY8FPYFElNjKN"

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

        handleConnectNodes(
            CanvasMemoAction.ConnectNodes(
                fromId = "1",
                toId = "2",
                name = "로직 테스트 연결"
            )
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

            is CanvasMemoAction.ConnectNodes -> handleConnectNodes(action)

            is CanvasMemoAction.OnBottomBarClick -> handleBottomBarClick(action)

            CanvasMemoAction.CancelPlaceItem -> handleCancelPlaceItem()

            is CanvasMemoAction.TapCanvas -> handleTapCanvas(action.tapPositionOnScreen)
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
                isRelationDialogVisible = false,
                relationSelection = null
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
        val editor = CanvasMemoEditor(getCurrentGraph())
        val updatedGraph = editor.moveNode(action.nodeId, action.newOffset)
        val movedNode = updatedGraph.nodes[action.nodeId] ?: return

        _uiState.update {
            it.copy(
                nodes = it.nodes + (action.nodeId to movedNode.toUiModel())
            )
        }
    }


    private fun handleConnectNodes(action: CanvasMemoAction.ConnectNodes) {
        val editor = CanvasMemoEditor(getCurrentGraph())
        val updatedGraph = editor.connectNode(action.fromId, action.toId, action.name)

        _uiState.update {
            it.copy(
                edges = updatedGraph.edges.map { it.toUiModel() },
                isRelationDialogVisible = false
            )
        }
    }

    private fun handleBottomBarClick(action: CanvasMemoAction.OnBottomBarClick) {
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
}
