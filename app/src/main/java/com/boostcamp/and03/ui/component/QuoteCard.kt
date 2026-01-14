package com.boostcamp.and03.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun QuoteCard(
    quote: QuoteUiModel,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(And03Radius.RADIUS_M),
        colors = CardDefaults.cardColors(containerColor = And03Theme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(And03Padding.PADDING_XL)
        ) {
            Text(
                text = quote.content,
                style = And03Theme.typography.bodyMedium,
                color = And03Theme.colors.onSurface,
                modifier = Modifier.padding(bottom = And03Padding.PADDING_2XL)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(
                        id = R.string.page_indicator,
                        quote.page
                    ),
                    style = And03Theme.typography.bodySmall,
                    color = And03Theme.colors.secondary
                )
                Text(
                    text = quote.date,
                    style = And03Theme.typography.bodySmall,
                    color = And03Theme.colors.secondary
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun QuoteCardPreview() {
    QuoteCard(
        quote = QuoteUiModel(
            id = "1",
            content = "이 책을 읽으면서 꿈에 대한 새로운 관점을 얻게 되었다. 꿈이 단순히 무의식의 산물이 아니라 우리가 구매할 수 있는 상품이라는 설정이 흥미롭다.",
            page = 26,
            date = "2026.1.7",
        ),
        onClick = {}
    )
}