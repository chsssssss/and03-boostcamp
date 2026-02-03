package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.IconBadge
import com.boostcamp.and03.ui.screen.bookdetail.component.DropdownMenuContainer
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

private object NodeItemValues {
    val width = 180.dp
    val height = 240.dp

    val borderWidth = 2.dp
    val moreIconSize = 20.dp
    val iconAreaHeight = 48.dp

    const val TITLE_MAX_LINES = 1
    const val CONTENT_MAX_LINES = 6
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
            .size(NodeItemValues.width, NodeItemValues.height)
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = And03Padding.PADDING_L,
                    start = And03Padding.PADDING_M,
                    end = And03Padding.PADDING_M,
                    bottom = And03Padding.PADDING_M
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(NodeItemValues.iconAreaHeight),
                contentAlignment = Alignment.Center
            ) {
                IconBadge(
                    iconResId = R.drawable.ic_person_filled,
                    iconColor = And03Theme.colors.primary,
                    contentDescription = stringResource(R.string.content_description_node_item_icon)
                )
            }

            Spacer(modifier = Modifier.height(And03Spacing.SPACE_S))

            Text(
                text = title,
                style = And03Theme.typography.titleMedium,
                maxLines = NodeItemValues.TITLE_MAX_LINES,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(And03Spacing.SPACE_S))

            Text(
                modifier = Modifier.weight(1f),
                text = content,
                style = And03Theme.typography.bodyMedium,
                color = And03Theme.colors.onSurfaceVariant,
                maxLines = NodeItemValues.CONTENT_MAX_LINES,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NodeItemPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_L),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NodeItem(
            title = "노드 제목",
            content = "짧은 내용",
            isHighlighted = false
        )

        NodeItem(
            title = "노드 제목",
            content = """
                가나다라가나다라가나다라가나다라가나다라가나다라가
                나다라가나다라가나다라가나다라가나
                다라가나다라가나다라가나다라가나다라가
                나다라가나다라가나다라
            """.trimIndent(),
            isHighlighted = true
        )
    }
}
