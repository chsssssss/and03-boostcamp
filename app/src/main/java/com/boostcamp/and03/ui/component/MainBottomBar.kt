package com.boostcamp.and03.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03IconSize
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Spacing

@Composable
fun MainBottomBar(
    items: List<MainBottomBarItem>,
    selectedType: MainBottomBarType,
    onItemClick: (MainBottomBarType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row( // 추후에 background 설정하기 (가져와서 쓸경우)
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = And03Spacing.SPACE_M),
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

@Composable
private fun MainBottomBarButton(
    item: MainBottomBarItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(And03ComponentSize.BOTTOM_BAR_ITEM_SIZE)
                .background(
                    color = item.backgroundColor.copy(
                        alpha = if (isSelected) 1f else 0.6f
                    ),
                    shape = RoundedCornerShape(And03Radius.RADIUS_L)
                ),
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
