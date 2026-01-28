package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import com.boostcamp.and03.ui.theme.And03Border
import com.boostcamp.and03.ui.theme.And03IconSize
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03ComponentSize

@Composable
fun QuoteItem(
    quote: QuoteUiModel,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFFFFAE8),
                shape = RoundedCornerShape(And03Radius.RADIUS_L)
            )
            .then(
                if (onClick != null) Modifier.clickable { onClick() }
                else Modifier
            )
            .padding(And03Spacing.SPACE_L),
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
                text = quote.content,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = stringResource(
                    id = R.string.page_indicator,
                    quote.page
                ),
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )
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
            quote = QuoteUiModel(
                id = "1",
                content = "어른들은 숫자를 좋아한다. 나이를 물으면 그 사람이 어떤 사람인지 알았다고 생각한다.",
                page = 42,
                date=""
            )
        )
    }
}
