package com.boostcamp.and03.ui.screen.booklist.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BooklistTitle() {
    Text(
        text = "책 보관함",
        style = MaterialTheme.typography.titleLarge
    )
}
