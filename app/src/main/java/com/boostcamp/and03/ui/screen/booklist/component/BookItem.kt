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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.boostcamp.and03.ui.theme.Dimensions
import kotlinx.collections.immutable.ImmutableList

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
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun BookAuthor(authors: ImmutableList<String>) {
    Text(
        text = authors.joinToString(", "),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}