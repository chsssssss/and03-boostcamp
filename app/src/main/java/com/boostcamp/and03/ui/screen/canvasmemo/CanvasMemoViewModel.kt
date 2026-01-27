package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.domain.editor.CanvasMemoEditor
import com.boostcamp.and03.domain.factory.MemoGraphFactory
import com.boostcamp.and03.domain.model.MemoGraph
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.toUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
import com.boostcamp.and03.ui.screen.canvasmemo.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
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
        viewModelScope.launch {
            createInitialState(
                userId = userId,
                bookId = bookId
            )
        }

        handleConnectNodes(
            CanvasMemoAction.ConnectNodes(
                fromId = "1",
                toId = "2",
                name = "로직 테스트 연결"
            )
        )
    }

    private suspend fun createInitialState(
        userId: String,
        bookId: String
    ) {
        val sampleGraph = MemoGraphFactory.createSample()
        val characters = getCharacters(
            userId = userId,
            bookId = bookId
        )
        val quotes = getQuotes(
            userId = userId,
            bookId = bookId
        )

        _uiState.update {
            it.copy(
                nodes = sampleGraph.nodes.mapValues { it.value.toUiModel() },
                edges = sampleGraph.edges.map { it.toUiModel() },
                characters = characters,
                quotes = quotes,
                totalPage = totalPage
            )
        }
    }

    private fun getCurrentGraph(): MemoGraph {
        val nodes = _uiState.value.nodes.mapValues { it.value.node }
        val edges = _uiState.value.edges.map { it.edge }
        return MemoGraph(nodes, edges)
    }

    private suspend fun getCharacters(
        userId: String,
        bookId: String
    ): ImmutableList<CharacterUiModel> {
        return bookStorageRepository.getCharacters(userId, bookId)
            .map { it.toUiModel() }
            .toImmutableList()
    }

    private suspend fun getQuotes(
        userId: String,
        bookId: String
    ): ImmutableList<QuoteUiModel> {
        return bookStorageRepository.getQuotes(userId, bookId)
            .map { it.toUiModel() }
            .toImmutableList()
    }

    fun onAction(action: CanvasMemoAction) {
        when (action) {
            CanvasMemoAction.ClickBack -> handleClickBack()

            CanvasMemoAction.CloseBottomSheet -> handleCloseBottomSheet()

            CanvasMemoAction.CloseRelationDialog -> handleCloseRelationDialog()

            is CanvasMemoAction.OpenRelationDialog -> handleOpenRelationDialog(action)

            CanvasMemoAction.CloseAddCharacterDialog -> handleCloseAddCharacterDialog()

            CanvasMemoAction.CloseQuoteDialog -> handleCloseQuoteDialog()

            CanvasMemoAction.AddQuoteItem -> handleAddQuote()

            is CanvasMemoAction.SearchQuote -> handleSearchQuote(action)

            CanvasMemoAction.AddNewQuote -> handleAddNewQuote()

            is CanvasMemoAction.MoveNode -> handleMoveNode(action)

            is CanvasMemoAction.ConnectNodes -> handleConnectNodes(action)

            is CanvasMemoAction.OnBottomBarClick -> handleBottomBarClick(action)
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
                relationSelection = null,
                relationNameState = TextFieldState()
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
                ),
                relationNameState = TextFieldState()
            )
        }
    }

    private fun handleCloseAddCharacterDialog() {
        _uiState.value = _uiState.value.copy(
            isAddCharacterDialogVisible = false,
            characterNameState = TextFieldState(),
            characterDescState = TextFieldState()
        )
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

    private fun handleAddQuote() {
        _uiState.update {
            it.copy(
                bottomSheetType = null
            )
        }
    }

    private fun handleSearchQuote(action: CanvasMemoAction.SearchQuote) {
        // TODO: 구절 검색 동작 연동
    }

    private fun handleAddNewQuote() {
        _uiState.update {
            it.copy(
                isQuoteDialogVisible = true,
                bottomSheetType = null,
                quoteState = TextFieldState(),
                pageState = TextFieldState()
            )
        }
    }

    private fun handleMoveNode(action: CanvasMemoAction.MoveNode) {
        val editor = CanvasMemoEditor(getCurrentGraph())
        val updatedGraph = editor.moveNode(action.nodeId, action.newOffset)

        val movedNode = updatedGraph.nodes[action.nodeId] ?: return

        _uiState.update { currentState ->
            currentState.copy(
                nodes = currentState.nodes + (action.nodeId to movedNode.toUiModel())
            )
        }
    }


    private fun handleConnectNodes(action: CanvasMemoAction.ConnectNodes) {
        val editor = CanvasMemoEditor(getCurrentGraph())
        val updatedGraph = editor.connectNode(action.fromId, action.toId, action.name)

        _uiState.update { currentState ->
            currentState.copy(
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
}