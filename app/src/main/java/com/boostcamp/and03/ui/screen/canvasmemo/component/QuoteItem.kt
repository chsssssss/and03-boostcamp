package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.screen.bookdetail.component.DropdownMenuContainer
import com.boostcamp.and03.ui.theme.And03Border
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03IconSize
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Spacing

private object QuoteItemValues {
    val MORE_ICON_SIZE = 20.dp
}

@Composable
fun QuoteItem(
    quote: String,
    page: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    onEditClick: (() -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .widthIn(max = And03ComponentSize.QUOTE_ITEM_MAX_WIDTH)
            .background(
                color = Color(0xFFFFFAE8),
                shape = RoundedCornerShape(And03Radius.RADIUS_L)
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            )
            .padding(And03Spacing.SPACE_L)
    ) {
        DropdownMenuContainer(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(
                    x = And03Spacing.SPACE_L,
                    y = (-And03Spacing.SPACE_L)
                ),
            trigger = { onMenuClick ->
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_more_vert_filled),
                        contentDescription = stringResource(R.string.content_description_more_button),
                        modifier = Modifier.size(QuoteItemValues.MORE_ICON_SIZE)
                    )
                }
            },
            menuContent = { closeMenu ->
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.more_vert_edit)) },
                    onClick = {
                        closeMenu()
                        // onEditClick()
                    }
                )

                DropdownMenuItem(
                    text = { Text(stringResource(R.string.more_vert_delete)) },
                    onClick = {
                        closeMenu()
                        // onDeleteClick()
                    }
                )
            }
        )

        Row(
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .width(And03Border.LEFT_BAR_WIDTH)
                    .height(And03ComponentSize.QUOTE_LEFT_BAR_HEIGHT)
                    .background(
                        color = Color(0xFFFFA500),
                        shape = RoundedCornerShape(And03Radius.RADIUS_XS)
                    )
            )

            Spacer(modifier = Modifier.width(And03Spacing.SPACE_M))

            Column(
                verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = Color(0xFFFFA500),
                    modifier = Modifier.size(And03IconSize.ICON_SIZE_S)
                )

                Text(
                    text = quote,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )

                Text(
                    text = stringResource(
                        id = R.string.page_indicator,
                        page
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuoteItemPreview() {
    Surface(
        modifier = Modifier.padding(16.dp)
    ) {
        QuoteItem(
            quote = "어른들은 숫자를 좋아한다. 나이를 물으면 그 사람이 어떤 사람인지 알았다고 생각한다.",
            page = 42
        )
    }
}
