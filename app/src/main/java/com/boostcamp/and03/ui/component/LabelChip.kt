package com.boostcamp.and03.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun LabelChip(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = And03Theme.colors.secondaryContainer,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 2.dp
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = And03Theme.colors.onSecondaryContainer
        )
    }
}