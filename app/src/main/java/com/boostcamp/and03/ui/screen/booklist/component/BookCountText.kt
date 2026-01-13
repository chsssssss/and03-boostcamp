package com.boostcamp.and03.ui.screen.booklist.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.boostcamp.and03.R

@Composable
fun BookCountText(count: Int) {
    Text(
        text = stringResource(
            id = R.string.book_count_text,
            arrayOf(count)
        ),
        style = MaterialTheme.typography.bodyMedium
    )
}