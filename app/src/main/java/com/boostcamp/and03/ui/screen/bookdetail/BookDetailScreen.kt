package com.boostcamp.and03.ui.screen.bookdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.component.CharacterCard
import com.boostcamp.and03.ui.component.EmptyDataScreen
import com.boostcamp.and03.ui.component.QuoteCard
import com.boostcamp.and03.ui.screen.bookdetail.component.DropdownMenuContainer
import com.boostcamp.and03.ui.screen.bookdetail.component.MemoCard
import com.boostcamp.and03.ui.screen.bookdetail.component.SquareAddButton
import com.boostcamp.and03.ui.screen.bookdetail.model.BookDetailTab
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.MemoType
import com.boostcamp.and03.ui.screen.bookdetail.model.MemoUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.util.collectWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@Composable
fun BookDetailRoute(
    navigateBack: () -> Unit,
    navigateToCharacterForm: (bookId: String, characterId: String) -> Unit,
    navigateToQuoteForm: (bookId: String, quoteId: String) -> Unit,
    navigateToTextMemoForm: (bookId: String, memoId: String) -> Unit,
    navigateToCanvasMemoForm: (bookId: String, memoId: String) -> Unit,
    navigateToCanvas: (memoId: String) -> Unit,
    viewModel: BookDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.event.collectWithLifecycle { event ->
        when (event) {
            BookDetailEvent.NavigateBack -> navigateBack()

            is BookDetailEvent.NavigateToCharacterForm -> {
                navigateToCharacterForm(
                    event.bookId,
                    event.characterId
                )
            }

            is BookDetailEvent.NavigateToQuoteForm -> {
                navigateToQuoteForm(
                    event.bookId,
                    event.quoteId
                )
            }

            is BookDetailEvent.NavigateToTextMemoForm -> {
                navigateToTextMemoForm(
                    event.bookId,
                    event.memoId
                )
            }

            is BookDetailEvent.NavigateToCanvasMemoForm -> {
                navigateToCanvasMemoForm(
                    event.bookId,
                    event.memoId
                )
            }

            is BookDetailEvent.NavigateToCanvas -> { navigateToCanvas(event.memoId) }
        }
    }

    BookDetailScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun BookDetailScreen(
    uiState: BookDetailUiState,
    onAction: (BookDetailAction) -> Unit,
) {
    val tabs = BookDetailTab.entries
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = { tabs.size })

    LaunchedEffect(
        pagerState.currentPage,
        pagerState.isScrollInProgress
    ) {
        if (!pagerState.isScrollInProgress) {
            onAction(BookDetailAction.OnTabSelect(pagerState.currentPage))
        }
    }

    Scaffold(
        topBar = {
            And03AppBar(
                title = stringResource(R.string.book_detail_app_bar_title),
                onBackClick = { onAction(BookDetailAction.OnBackClick) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.isLoadingBookInfo) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.errorMessage != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_M),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = uiState.errorMessage)
                        Button(onClick = { onAction(BookDetailAction.OnRetryClick) }) {
                            Text(stringResource(R.string.retry_btn_txt))
                        }
                    }
                }
            } else {
                BookInfoSection(
                    thumbnail = uiState.thumbnail,
                    title = uiState.title,
                    author = uiState.author,
                    publisher = uiState.publisher,
                    totalPage = uiState.totalPage
                )

                SecondaryTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = And03Theme.colors.surface,
                    contentColor = And03Theme.colors.onSurface,
                    indicator = {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(pagerState.currentPage),
                            color = And03Theme.colors.primary
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabs.forEachIndexed { index, tab ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { scope.launch {
                                pagerState.animateScrollToPage(index)
                            } },
                            text = { Text(text = tab.title) }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { selectedTabIndex ->
                    when (tabs[selectedTabIndex]) {
                        BookDetailTab.CHARACTER -> CharacterTab(
                            characters = uiState.characters,
                            isLoading = uiState.isLoadingCharacters,
                            onClickAdd = {
                                onAction(
                                    BookDetailAction.OnOpenCharacterForm(
                                        uiState.bookId,
                                        ""
                                    )
                                )
                            },
                            onClickDelete = { characterId ->
                                onAction(BookDetailAction.DeleteCharacter(characterId))
                            },
                            onClickEdit = { characterId ->
                                onAction(
                                    BookDetailAction.OnOpenCharacterForm(
                                        uiState.bookId,
                                        characterId
                                    )
                                )
                            }
                        )

                        BookDetailTab.QUOTE -> QuoteTab(
                            quotes = uiState.quotes,
                            isLoading = uiState.isLoadingQuotes,
                            onClickAdd = {
                                onAction(
                                    BookDetailAction.OnOpenQuoteForm(
                                        uiState.bookId,
                                        ""
                                    )
                                )
                            },
                            onClickDelete = { quoteId ->
                                onAction(BookDetailAction.DeleteQuote(quoteId))
                            },
                            onClickEdit = { quoteId ->
                                onAction(
                                    BookDetailAction.OnOpenQuoteForm(
                                        uiState.bookId,
                                        quoteId
                                    )
                                )
                            }
                        )

                        BookDetailTab.MEMO -> MemoTab(
                            memos = uiState.memos,
                            isLoading = uiState.isLoadingMemos,
                            onClickAddCanvas = {
                                onAction(
                                    BookDetailAction.OnOpenCanvasMemoForm(
                                        uiState.bookId,
                                        ""
                                    )
                                )
                            },
                            onClickAddText = {
                                onAction(
                                    BookDetailAction.OnOpenTextMemoForm(
                                        uiState.bookId,
                                        ""
                                    )
                                )
                            },
                            onClickMemo = { memo ->
                                if (memo.memoType == MemoType.CANVAS) {
                                    onAction(BookDetailAction.OnCanvasMemoClick(memo.id))
                                }
                            },
                            onClickDelMemo = { memoId ->
                                onAction(BookDetailAction.DeleteMemo(memoId))
                            },
                            onClickEditMemo = { memo ->
                                when (memo.memoType) {
                                    MemoType.TEXT -> onAction(
                                        BookDetailAction.OnOpenTextMemoForm(
                                            uiState.bookId,
                                            memo.id
                                        )
                                    )

                                    MemoType.CANVAS -> onAction(
                                        BookDetailAction.OnOpenCanvasMemoForm(
                                            uiState.bookId,
                                            memo.id
                                        )
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookInfoSection(
    thumbnail: String,
    title: String,
    author: String,
    publisher: String,
    totalPage: Int
) {
    Row(
        modifier = Modifier.padding(And03Padding.PADDING_XL),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = thumbnail,
            contentDescription = null,
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(2f / 3f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(And03Spacing.SPACE_M))
        Column(
            modifier = Modifier.padding(vertical = And03Padding.PADDING_2XL),
        ) {
            Text(
                text = title,
                style = And03Theme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(And03Spacing.SPACE_S))
            Text(
                text = author,
                style = And03Theme.typography.bodyMedium,
                color = And03Theme.colors.secondary
            )
            Spacer(modifier = Modifier.height(And03Spacing.SPACE_XS))
            Text(
                text = publisher,
                style = And03Theme.typography.bodySmall,
                color = And03Theme.colors.secondary
            )
            Spacer(modifier = Modifier.height(And03Spacing.SPACE_XS))
            Text(
                text = stringResource(
                    id = R.string.book_detail_total_page,
                    totalPage
                ),
                style = And03Theme.typography.bodySmall,
                color = And03Theme.colors.secondary
            )
        }
    }
}

@Composable
private fun CharacterTab(
    characters: ImmutableList<CharacterUiModel>,
    isLoading: Boolean,
    onClickAdd: () -> Unit,
    onClickDelete: (String) -> Unit,
    onClickEdit: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(And03Padding.PADDING_L)
    ) {
        when {
            isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            characters.isEmpty() -> EmptyDataScreen()

            else -> {
                LazyColumn(
                    modifier = Modifier.align(Alignment.TopStart),
                    verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_M)
                ) {
                    items(
                        items = characters,
                        key = { it.id }
                    ) { character ->
                        CharacterCard(
                            name = character.name,
                            role = character.role,
                            iconColor = character.iconColor,
                            description = character.description,
                            onClick = { },
                            onEditClick = { onClickEdit(character.id) },
                            onDeleteClick = { onClickDelete(character.id) }
                        )
                    }
                }
            }
        }

        SquareAddButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = onClickAdd
        )
    }
}

@Composable
private fun QuoteTab(
    quotes: ImmutableList<QuoteUiModel>,
    isLoading: Boolean,
    onClickAdd: () -> Unit,
    onClickDelete: (String) -> Unit,
    onClickEdit: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(And03Padding.PADDING_L)
    ) {
        when {
            isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            quotes.isEmpty() -> EmptyDataScreen()

            else -> {
                LazyColumn(
                    modifier = Modifier.align(Alignment.TopStart),
                    verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_M)
                ) {
                    items(quotes, key = { it.id }) { quote ->
                        QuoteCard(
                            quote = quote,
                            onClick = {},
                            onClickDelete = { onClickDelete(quote.id) },
                            onClickEdit = { onClickEdit(quote.id) },
                        )
                    }
                }
            }
        }

        SquareAddButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = onClickAdd
        )
    }
}

@Composable
private fun MemoTab(
    memos: ImmutableList<MemoUiModel>,
    isLoading: Boolean,
    onClickAddCanvas: () -> Unit,
    onClickAddText: () -> Unit,
    onClickMemo: (MemoUiModel) -> Unit,
    onClickDelMemo: (String) -> Unit,
    onClickEditMemo: (MemoUiModel) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(And03Padding.PADDING_L)
    ) {
        when {
            isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            memos.isEmpty() -> EmptyDataScreen()

            else -> {
                LazyColumn(
                    modifier = Modifier.align(Alignment.TopStart),
                    verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_M)
                ) {
                    items(memos, key = { it.id }) { memo ->
                        val pageLabelText = if (memo.startPage == memo.endPage) {
                            stringResource(
                                id = R.string.book_detail_memo_single_page,
                                memo.startPage
                            )
                        } else {
                            stringResource(
                                id = R.string.book_detail_memo_page_range,
                                memo.startPage,
                                memo.endPage
                            )
                        }

                        MemoCard(
                            type = memo.memoType,
                            title = memo.title,
                            contentPreview = memo.content ?: "",
                            pageLabel = pageLabelText,
                            date = memo.date,
                            onClick = { onClickMemo(memo) },
                            onClickDelMemo = { onClickDelMemo(memo.id) },
                            onClickEdit = { onClickEditMemo(memo) },
                        )
                    }
                }
            }
        }

        DropdownMenuContainer(
            modifier = Modifier.align(Alignment.BottomEnd),
            trigger = { onClick ->
                SquareAddButton(onClick = onClick)
            },
            menuContent = { closeMenu ->
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.book_detail_add_canvas_memo)) },
                    onClick = {
                        closeMenu()
                        onClickAddCanvas()
                    }
                )

                DropdownMenuItem(
                    text = { Text(stringResource(R.string.book_detail_add_text_memo)) },
                    onClick = {
                        closeMenu()
                        onClickAddText()
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BooklistScreenPreview() {
    val previewState = BookDetailUiState(
        thumbnail = "https://i.pinimg.com/736x/8f/20/41/8f2041520696507bc2bfd2f5648c8da3.jpg",
        title = "Harry Potter and the Philosopher's Stone",
        author = "J.K. Rowling",
        publisher = "Bloomsbury Publishing",
        characters = persistentListOf(
            CharacterUiModel(
                id = "1",
                name = "해리 포터",
                role = "주인공",
                iconColor = Color(0xFF1E88E5),
                description = "호그와트의 마법사 학생으로 볼드모트와 맞서 싸우는 주인공"
            ),
            CharacterUiModel(
                id = "2",
                name = "헤르미온느 그레인저",
                role = "조연",
                iconColor = Color(0xFF8E24AA),
                description = "뛰어난 마법 실력을 가진 해리의 절친한 친구"
            )
        ),
    )

    And03Theme {
        BookDetailScreen(
            uiState = previewState,
            onAction = {}
        )
    }
}