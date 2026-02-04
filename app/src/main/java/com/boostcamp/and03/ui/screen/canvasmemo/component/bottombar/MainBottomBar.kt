package com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.screen.canvasmemo.CanvasMemoAction
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03IconSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.theme.CanvasMemoColors

@Composable
fun MainBottomBar(
    items: List<MainBottomBarItem>,
    selectedType: MainBottomBarType,
    onItemClick: (MainBottomBarType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = And03Theme.colors.background)
            .padding(vertical = And03Padding.PADDING_M),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEach { item ->
            MainBottomBarButton(
                item = item,
                isSelected = item.type == selectedType,
                onClick = { onItemClick(item.type) }
            )
        }
    }
}

/**
 * MainBottomBarButton을 직접 가져와 사용하는 컴포저블입니다.
 */
@Composable
fun MainBottomBar(
    selectedType: MainBottomBarType,
    onItemClick: (MainBottomBarType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = And03Theme.colors.background)
            .padding(vertical = And03Padding.PADDING_M),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MainBottomBarButton(
            item = MainBottomBarItem(
                type = MainBottomBarType.NODE,
                label = stringResource(R.string.canvas_bottom_bar_node),
                icon = Icons.Default.PersonAdd,
                backgroundColor = CanvasMemoColors.Node,
            ),
            isSelected = selectedType == MainBottomBarType.NODE,
            onClick = { onItemClick(MainBottomBarType.NODE) }
        )

        MainBottomBarButton(
            item = MainBottomBarItem(
                type = MainBottomBarType.RELATION,
                label = stringResource(R.string.canvas_bottom_bar_relation),
                icon = Icons.Default.Link,
                backgroundColor = CanvasMemoColors.Relation
            ),
            isSelected = selectedType == MainBottomBarType.RELATION,
            onClick = { onItemClick(MainBottomBarType.RELATION) }
        )

        MainBottomBarButton(
            item = MainBottomBarItem(
                type = MainBottomBarType.QUOTE,
                label = stringResource(R.string.canvas_bottom_bar_quote),
                icon = Icons.Default.FormatQuote,
                backgroundColor = CanvasMemoColors.Quote
            ),
            isSelected = selectedType == MainBottomBarType.QUOTE,
            onClick = { onItemClick(MainBottomBarType.QUOTE) }
        )

        MainBottomBarButton(
            item = MainBottomBarItem(
                type = MainBottomBarType.DELETE,
                label = stringResource(R.string.canvas_bottom_bar_delete),
                icon = Icons.Default.Delete,
                backgroundColor = CanvasMemoColors.Delete
            ),
            isSelected = selectedType == MainBottomBarType.DELETE,
            onClick = { onItemClick(MainBottomBarType.DELETE) }
        )
    }
}

@Composable
private fun MainBottomBarButton(
    item: MainBottomBarItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(And03ComponentSize.BOTTOM_BAR_ITEM_SIZE)
                .clip(RoundedCornerShape(And03Radius.RADIUS_L))
                .background(
                    color = item.backgroundColor.copy(
                        alpha = if (isSelected) 1f else 0.6f
                    ),
                    shape = RoundedCornerShape(And03Radius.RADIUS_L)
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = Color.White,
                modifier = Modifier.size(And03IconSize.ICON_SIZE_M)
            )
        }

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_S))

        Text(
            text = item.label,
            style = MaterialTheme.typography.labelMedium,
            color = if (isSelected) Color.Black else Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainBottomBarPreview() {
    val items = listOf(
        MainBottomBarItem(
            type = MainBottomBarType.NODE,
            label = "노드",
            icon = Icons.Default.PersonAdd,
            backgroundColor = Color(0xFF6C63FF)
        ),
        MainBottomBarItem(
            type = MainBottomBarType.RELATION,
            label = "관계",
            icon = Icons.Default.Link,
            backgroundColor = Color(0xFF8B5CF6)
        ),
        MainBottomBarItem(
            type = MainBottomBarType.QUOTE,
            label = "구절",
            icon = Icons.Default.FormatQuote,
            backgroundColor = Color(0xFFFFA500)
        ),
        MainBottomBarItem(
            type = MainBottomBarType.DELETE,
            label = "삭제",
            icon = Icons.Default.Delete,
            backgroundColor = Color(0xFFFF4D4D)
        )
    )

    Surface {
        MainBottomBar(
            items = items,
            selectedType = MainBottomBarType.RELATION,
            onItemClick = {}
        )
    }
}
