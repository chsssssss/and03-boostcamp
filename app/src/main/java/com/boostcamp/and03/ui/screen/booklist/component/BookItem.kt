package com.boostcamp.and03.ui.screen.booklist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.theme.Dimensions
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BookItem(
    title: String,
    authors: ImmutableList<String>,
    thumbnail: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(Dimensions.PADDING_XS)
    ) {
        BookThumbnail(thumbnail)
        BookTitle(title)
        BookAuthor(authors)
    }
}

@Composable
private fun BookThumbnail(thumbnail: String) {
    AsyncImage(
        model = thumbnail,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f / 3f)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun BookTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun BookAuthor(authors: ImmutableList<String>) {
    Text(
        text = authors.joinToString(", "),
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodySmall
    )
}

@Preview(
    name = "BookItem Preview",
    showBackground = true,
    widthDp = 160
)
@Composable
private fun BookItemPreview() {
    And03Theme {
        BookItem(
            title = "클린 아키텍처: 소프트웨어 구조와 설계의 원칙 아주아주 긴 제목 테스트",
            authors = persistentListOf(
                "로버트 C. 마틴",
                "Uncle Bob",
                "Another Author"
            ),
            thumbnail = "https://books.google.com/books/content?id=R7zjDQAAQBAJ&printsec=frontcover&img=1&zoom=1"
        )
    }
}
