package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
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
import com.boostcamp.and03.ui.component.IconBadge
import com.boostcamp.and03.ui.screen.bookdetail.component.DropdownMenuContainer
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

private object NodeItemValues {
    val borderWidth = 2.dp
    val moreIconSize = 20.dp
}

@Composable
fun NodeItem(
    title: String,
    content: String,
    isHighlighted: Boolean,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isHighlighted) {
        And03Theme.colors.primary
    } else {
        And03Theme.colors.outline
    }

    Box(
        modifier = modifier
            .background(
                And03Theme.colors.surface,
                And03Theme.shapes.defaultCorner
            )
            .border(
                NodeItemValues.borderWidth,
                borderColor,
                And03Theme.shapes.defaultCorner
            )
    ) {
        DropdownMenuContainer(
            modifier = Modifier.align(Alignment.TopEnd),
            trigger = { onClick ->
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_more_vert_filled),
                        contentDescription = stringResource(R.string.content_description_more_button),
                        modifier = Modifier.size(NodeItemValues.moreIconSize)
                    )
                }
            },
            menuContent = { closeMenu ->
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.more_vert_edit)) },
                    onClick = {
                        closeMenu()
                    }
                )

                DropdownMenuItem(
                    text = { Text(stringResource(R.string.more_vert_delete)) },
                    onClick = {
                        closeMenu()
                    }
                )
            }
        )

        Column(
            modifier = Modifier.padding(And03Padding.PADDING_M),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S)
        ) {
            IconBadge(
                iconResId = R.drawable.ic_person_filled,
                iconColor = And03Theme.colors.primary,
                contentDescription = stringResource(R.string.content_description_node_item_icon)
            )

            Text(
                text = title,
                style = And03Theme.typography.titleMedium
            )

            Text(
                text = content,
                style = And03Theme.typography.bodyMedium,
                color = And03Theme.colors.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun NodeItemPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_L),
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
