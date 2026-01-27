package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03Button
import com.boostcamp.and03.ui.component.And03InfoSection
import com.boostcamp.and03.ui.component.ButtonVariant
import com.boostcamp.and03.ui.component.QuoteCard
import com.boostcamp.and03.ui.component.SearchTextField
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import com.boostcamp.and03.ui.theme.And03BorderWidth
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03IconSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.util.drawVerticalScrollbar

@Composable
fun AddQuoteBottomSheet(
    modifier: Modifier = Modifier,
    quotes: List<QuoteUiModel> = emptyList(),
    onAddClick: () -> Unit = {},
    onNewSentenceClick: () -> Unit = {},
    onSearch: (String) -> Unit = {},
) {
    val searchState = rememberTextFieldState()
    val listState = rememberLazyListState()
    var selectedQuoteId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = And03Theme.colors.background,
                shape = RoundedCornerShape(
                    topStart = And03Radius.RADIUS_L,
                    topEnd = And03Radius.RADIUS_L
                )
            )
            .padding(
                horizontal = And03Padding.PADDING_L,
                vertical = And03Padding.PADDING_M
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.add_quote_bottom_sheet_title),
            style = And03Theme.typography.titleMedium,
            color = And03Theme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_L))

        And03InfoSection(
            title = stringResource(R.string.add_quote_bottom_sheet_info_title),
            description = stringResource(R.string.add_quote_bottom_sheet_info_description)
        )

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_M))

        SearchTextField(
            state = searchState,
            onSearch = { onSearch(searchState.text.toString()) },
            modifier = Modifier.fillMaxWidth(),
            placeholderRes = R.string.add_quote_bottom_sheet_search_placeholder
        )

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_M))

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f, fill = false)
                .nestedScroll(rememberNestedScrollInteropConnection())
                .drawVerticalScrollbar(listState, color = And03Theme.colors.outlineVariant),
            verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S),
            contentPadding = PaddingValues(bottom = And03Padding.PADDING_M)
        ) {
            items(
                items = quotes,
                key = { it.id }
            ) { quote ->
                val isSelected = selectedQuoteId == quote.id

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = And03BorderWidth.BORDER_2,
                            color = if (isSelected) And03Theme.colors.primary else Color.Transparent,
                            shape = RoundedCornerShape(And03Radius.RADIUS_M)
                        )
                ) {
                    QuoteCard(
                        quote = quote,
                        onClick = {
                            if (selectedQuoteId == null || selectedQuoteId != quote.id) {
                                selectedQuoteId = quote.id
                            } else {
                                selectedQuoteId = null
                            }
                        },
                    )
                }
            }
        }

        TextButton(
            onClick = onNewSentenceClick,
            modifier = Modifier.padding(vertical = And03Spacing.SPACE_S)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(And03IconSize.ICON_SIZE_S)
            )

            Spacer(modifier = Modifier.width(And03Spacing.SPACE_XS))

            Text(
                text = stringResource(R.string.add_quote_bottom_sheet_new_sentence),
                style = And03Theme.typography.labelLarge
            )
        }

        And03Button(
            text = stringResource(R.string.add_quote_bottom_sheet_button_add),
            onClick = onAddClick,
            variant = ButtonVariant.Primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(And03ComponentSize.BUTTON_HEIGHT_L),
            enabled = selectedQuoteId != null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddQuoteBottomSheetPreview() {
    val dummyQuotes = List(10) { i ->
        QuoteUiModel(
            id = i.toString(),
            content = "이 책을 읽으면서 꿈에 대한 새로운 관점을 얻게 되었다. $i",
            page = 10 + i,
            date = "2026.01.14"
        )
    }

    And03Theme {
        AddQuoteBottomSheet(quotes = dummyQuotes)
    }
}