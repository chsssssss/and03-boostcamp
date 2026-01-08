package com.boostcamp.and03.ui.screen.bookdetail

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.MemoType
import com.boostcamp.and03.ui.screen.bookdetail.model.MemoUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import jakarta.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookDetailUiState())
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    init {
        initPreviewData()
    }

    private fun initPreviewData() {
        _uiState.value = BookDetailUiState(
            thumbnail = "https://i.pinimg.com/736x/8f/20/41/8f2041520696507bc2bfd2f5648c8da3.jpg",
            title = "Harry Potter and the Philosopher's Stone",
            author = "J.K. Rowling",
            publisher = "Bloomsbury Publishing",
            characters = persistentListOf(
                CharacterUiModel(
                    name = "해리 포터",
                    role = "주인공",
                    iconColor = Color(0xFF1E88E5),
                    description = "호그와트의 마법사 학생"
                ),
                CharacterUiModel(
                    name = "헤르미온느 그레인저",
                    role = "조연",
                    iconColor = Color(0xFF8E24AA),
                    description = "해리의 절친한 친구"
                )
            ),
            quotes = persistentListOf(
                QuoteUiModel(
                    "1",
                    "이 책을 읽으면서 꿈에 대한 새로운 관점을 얻게 되었다. 꿈이 단순히 무의식의 산물이 아니라 우리가 구매할 수 있는 상품이라는 설정이 흥미롭다.",
                    26,
                    "2024.01.10"
                ),
                QuoteUiModel(
                    "2",
                    "이 책을 읽으면서 꿈에 대한 새로운 관점을 얻게 되었다. 꿈이 단순히 무의식의 산물이 아니라 우리가 구매할 수 있는 상품이라는 설정이 흥미롭다.",
                    26,
                    "2024.01.10"
                ),
                QuoteUiModel(
                    "3",
                    "이 책을 읽으면서 꿈에 대한 새로운 관점을 얻게 되었다. 꿈이 단순히 무의식의 산물이 아니라 우리가 구매할 수 있는 상품이라는 설정이 흥미롭다.",
                    26,
                    "2024.01.10"
                )
            ),
            memos = persistentListOf(
                MemoUiModel(
                    id = "memo_1",
                    memoType = MemoType.CANVAS,
                    startPage = 1,
                    endPage = 26,
                    date = "2025.12.24",
                    title = "인물 관계도 정리",
                    content = null
                ),
                MemoUiModel(
                    id = "memo_2",
                    memoType = MemoType.TEXT,
                    startPage = 5,
                    endPage = 8,
                    date = "2025.12.24",
                    title = "마법 세계관 메모",
                    content = "해리 포터 세계관에서 마법은 타고난 재능과 훈련이 결합된 능력으로 묘사된다. 머글과 마법사의 대비가 인상적이었다."
                ),
                MemoUiModel(
                    id = "memo_3",
                    memoType = MemoType.TEXT,
                    startPage = 20,
                    endPage = 22,
                    date = "2025.12.25",
                    title = "인상 깊은 문장",
                    content = "선택이야말로 우리가 누구인지를 보여준다. 능력보다 더 중요한 것은 선택이라는 메시지가 강하게 남았다."
                ),
                MemoUiModel(
                    id = "memo_4",
                    memoType = MemoType.CANVAS,
                    startPage = 1,
                    endPage = 50,
                    date = "2025.12.26",
                    title = "주요 인물 관계도",
                    content = null
                )
            )
        )
    }
}