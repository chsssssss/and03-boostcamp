package com.boostcamp.and03.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

private object SearchResultItemValues {
    val borderWidth = 2.dp
    val itemHeight = 124.dp
    val thumbnailHeight = 100.dp
    const val TITLE_MAX_LINES = 2
    const val AUTHOR_PUBLISHER_MAX_LINES = 1
}

@Composable
fun SearchResultItem(
    thumbnail: String,
    title: String,
    author: String,
    publisher: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val borderColor = if (isSelected) {
        And03Theme.colors.primary
    } else {
        And03Theme.colors.outline
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(SearchResultItemValues.itemHeight)
            .border(
                width = SearchResultItemValues.borderWidth,
                color = borderColor,
                shape = And03Theme.shapes.defaultCorner
            )
            .background(
                color = And03Theme.colors.surface,
                shape = And03Theme.shapes.defaultCorner
            )
            .clickable(onClick = onClick)
            .padding(And03Padding.PADDING_M)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_M),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = thumbnail,
                contentDescription = title,
                modifier = Modifier
                    .height(SearchResultItemValues.thumbnailHeight)
                    .aspectRatio(2f / 3f)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = And03Padding.PADDING_M),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = title,
                    style = And03Theme.typography.titleMedium,
                    maxLines = SearchResultItemValues.TITLE_MAX_LINES,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = author,
                    style = And03Theme.typography.bodyMedium,
                    color = And03Theme.colors.onSurfaceVariant,
                    maxLines = SearchResultItemValues.AUTHOR_PUBLISHER_MAX_LINES,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = publisher,
                    style = And03Theme.typography.bodyMedium,
                    color = And03Theme.colors.onSurfaceVariant,
                    maxLines = SearchResultItemValues.AUTHOR_PUBLISHER_MAX_LINES,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchResultItemPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_L),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchResultItem(
            thumbnail = "",
            title = "책 제목",
            author = "책 저자",
            publisher = "책 출판사",
            isSelected = false,
            onClick = {}
        )

        SearchResultItem(
            thumbnail = "",
            title = """
                선택된 책 제목. 테두리 색이 바뀌었습니다. 그런데 여기서 개행을 해야 한다면...........????????????
            """.trimIndent(),
            author = "선택된 책 저자",
            publisher = "선택된 책 출판사",
            isSelected = true,
            onClick = {}
        )
    }
}