package com.boostcamp.and03.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.Dimensions
import com.boostcamp.and03.ui.theme.MainTheme

private object NodeItemValues {
    val borderWidth = 2.dp
    val moreIconSize = 20.dp
}

@Composable
fun NodeItem(
    title: String,
    content: String,
    isHighlighted: Boolean,
    modifier: Modifier = Modifier,
    onMoreClick: () -> Unit = {}
) {
    val borderColor = if (isHighlighted) {
        MainTheme.colors.primary
    } else {
        MainTheme.colors.outline
    }

    Box(
        modifier = modifier
            .background(
                MainTheme.colors.surface,
                MainTheme.shapes.defaultCorner
            )
            .border(
                NodeItemValues.borderWidth,
                borderColor,
                MainTheme.shapes.defaultCorner
            )
    ) {
        IconButton(
            onClick = onMoreClick,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_more_vert_filled),
                contentDescription = stringResource(R.string.content_description_more_button),
                modifier = Modifier.size(NodeItemValues.moreIconSize)
            )
        }

        Column(
            modifier = Modifier.padding(Dimensions.PADDING_M),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimensions.PADDING_S)
        ) {
            IconBadge(
                iconResId = R.drawable.ic_person_filled,
                iconColor = MainTheme.colors.primary
            )

            Text(
                text = title,
                style = MainTheme.typography.titleMedium
            )

            Text(
                text = content,
                style = MainTheme.typography.bodyMedium,
                color = MainTheme.colors.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun NodeItemPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimensions.PADDING_L),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NodeItem(
            title = "노드 제목",
            content = """
                일반적인 노드의 내용입니다.
                연속적으로
                줄바꿈을
                해봅시다.
                어떤가요?
                잘......      나오나요?
            """.trimIndent(),
            isHighlighted = false
        )

        NodeItem(
            title = "선택된 노드 제목",
            content = """
                선택된 노드의 내용입니다.
                테두리의 색깔이 primary 색상으로 바뀌었습니다.
            """.trimIndent(),
            isHighlighted = true
        )
    }
}