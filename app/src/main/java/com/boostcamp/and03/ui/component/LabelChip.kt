package com.boostcamp.and03.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun LabelChip(
    modifier: Modifier = Modifier,
    backgroundColor: Color = And03Theme.colors.secondaryContainer,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = And03Padding.PADDING_S,
        vertical = And03Padding.PADDING_2XS
    ),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(And03Radius.RADIUS_XS)
            )
            .padding(contentPadding)
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun ColoredLabelChipPreview() {
    LabelChip {
        Text(
            text = "p.1~26",
            style = And03Theme.typography.labelSmall,
            color = And03Theme.colors.onSecondaryContainer
        )
    }
}
