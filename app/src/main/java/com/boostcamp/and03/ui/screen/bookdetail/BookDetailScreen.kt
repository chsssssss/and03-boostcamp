package com.boostcamp.and03.ui.screen.bookdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.boostcamp.and03.ui.screen.bookdetail.component.CharacterCard
import com.boostcamp.and03.ui.screen.bookdetail.model.BookDetailTab
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun BookDetailRoute(
    viewModel: BookDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BookDetailScreen(
        uiState = uiState,
    )
}

@Composable
private fun BookDetailScreen(uiState: BookDetailUiState) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = BookDetailTab.entries

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SecondaryTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = And03Theme.colors.surface,
            contentColor = And03Theme.colors.onSurface,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(selectedTabIndex),
                    color = And03Theme.colors.primary
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, tab ->
                androidx.compose.material3.Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = tab.title
                        )
                    }
                )
            }
        }

        when (tabs[selectedTabIndex]) {
            BookDetailTab.CHARACTER -> CharacterTab(uiState)
            BookDetailTab.QUOTE -> ImpressiveQuoteTab()
            BookDetailTab.MEMO -> MemoTab()
        }

    }
}

@Composable
private fun BookInfoSection(
    thumbnail: String,
    title: String,
    author: String,
    publisher: String
) {
    Row(
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        AsyncImage(
            model = thumbnail,
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .aspectRatio(2f / 3f),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(vertical = 24.dp),
        ) {
            Text(
                text = title,
                style = And03Theme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = author,
                style = And03Theme.typography.bodyMedium,
                color = And03Theme.colors.secondary
            )
            Text(
                text = publisher,
                style = And03Theme.typography.bodySmall,
                color = And03Theme.colors.secondary
            )
        }
    }
}

@Composable
private fun CharacterTab(
    uiState: BookDetailUiState
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        uiState.characters.forEach { character ->
            CharacterCard(
                name = character.name,
                role = character.role,
                iconColor = character.iconColor,
                description = character.description,
                onMoreClick = { }
            )
        }
    }
}

@Composable
private fun ImpressiveQuoteTab() {
}

@Composable
private fun MemoTab() {

}


@Preview(showBackground = true)
@Composable
fun BooklistScreenPreview() {
    val previewState = BookDetailUiState(
        characters = listOf(
            CharacterUiModel(
                name = "해리 포터",
                role = "주인공",
                iconColor = Color(0xFF1E88E5),
                description = "호그와트의 마법사 학생으로 볼드모트와 맞서 싸우는 주인공"
            ),
            CharacterUiModel(
                name = "헤르미온느 그레인저",
                role = "조연",
                iconColor = Color(0xFF8E24AA),
                description = "뛰어난 마법 실력을 가진 해리의 절친한 친구"
            )
        )
    )

    And03Theme {
        BookDetailScreen(
            uiState = previewState,
        )
    }
}