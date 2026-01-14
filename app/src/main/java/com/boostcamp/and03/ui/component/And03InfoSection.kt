package com.boostcamp.and03.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.*

@Composable
fun And03InfoSection(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = And03Theme.colors.primaryContainer,
                shape = RoundedCornerShape(And03Radius.RADIUS_M)
            )
            .border(
                width = And03BorderWidth.BORDER_1,
                color = And03Theme.colors.primary,
                shape = RoundedCornerShape(And03Radius.RADIUS_M)
            )
            .padding(And03Padding.PADDING_L),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Lightbulb,
            contentDescription = null,
            tint = And03Theme.colors.primary,
            modifier = Modifier.size(And03IconSize.ICON_SIZE_S)
        )

        Spacer(modifier = Modifier.width(And03Spacing.SPACE_M))

        Column {
            Text(
                text = title,
                style = And03Theme.typography.labelLarge,
                color = And03Theme.colors.primary
            )
            Text(
                text = description,
                style = And03Theme.typography.labelSmall,
                color = And03Theme.colors.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun And03InfoSectionPreview() {
    And03Theme {
        And03InfoSection("인상 깊은 문장을 기록해보세요","책이나 영화, 일상에서 마음에 든 문장을 저장할 수 있습니다.")
    }
}