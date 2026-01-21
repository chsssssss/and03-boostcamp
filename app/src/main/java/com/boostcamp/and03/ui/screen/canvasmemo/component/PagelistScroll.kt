package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.ui.component.LabelChip
import com.boostcamp.and03.ui.component.drawVerticalScrollbar
import com.boostcamp.and03.ui.screen.canvasmemo.model.CanvasMemoSummaryUiModel
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import com.boostcamp.and03.R

@Composable
fun PagelistScroll(
    items: ImmutableList<CanvasMemoSummaryUiModel>,
    onItemClick: (CanvasMemoSummaryUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = And03ComponentSize.PAGE_LIST_SCROLL)
            .padding(And03Padding.PADDING_XS)
            .drawVerticalScrollbar(listState, color = And03Theme.colors.outlineVariant),
        verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_M),
        contentPadding = PaddingValues(
            vertical = And03Spacing.SPACE_M,
            horizontal = And03Spacing.SPACE_M
        )
    ) {
        items(
            items = items,
            key = { it.memoId }
        ) { item ->
            PagelistItem(
                title = item.title,
                startPage = item.startPage,
                endPage = item.endPage,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item) }
            )
        }
    }
}

@Composable
private fun PagelistItem(
    title: String,
    startPage: Int,
    endPage: Int,
    modifier: Modifier = Modifier
) {
    val pageText = if (startPage == endPage) {
        stringResource(
            id = R.string.canvas_memo_editor_page_list_scroll_single_page,
            startPage
        )
    } else {
        stringResource(
            id = R.string.canvas_memo_editor_page_list_scroll_page_range,
            startPage,
            endPage
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LabelChip {
            Text(
                text = pageText,
                style = And03Theme.typography.labelSmall,
                color = And03Theme.colors.onSecondaryContainer
            )
        }

        Text(
            text = title,
            style = And03Theme.typography.labelSmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PagelistScrollPreview() {
    PagelistScroll(
        items = persistentListOf(
            CanvasMemoSummaryUiModel(
                memoId = "memo-1",
                title = "캔버스 메모 아이템을 보여주는 리스트 스크롤 컴포넌트입니다.",
                startPage = 1,
                endPage = 1
            ),
            CanvasMemoSummaryUiModel(
                memoId = "memo-2",
                title = "페이지 범위 정보와 메모 제목을 출력하죠.",
                startPage = 5,
                endPage = 10
            ),
            CanvasMemoSummaryUiModel(
                memoId = "memo-3",
                title = "긴 메모 제목도 이렇게 들어올 수 있어요. 그런데 만약 화면 범위를 넘는 제목의 경우, 말줄임표 처리가 돼요.",
                startPage = 30,
                endPage = 45
            ),
            CanvasMemoSummaryUiModel(
                memoId = "memo-4",
                title = "인터랙션 모드를 쓰면 스크롤 체험도 가능해요!",
                startPage = 50,
                endPage = 60
            ),
            CanvasMemoSummaryUiModel(
                memoId = "memo-5",
                title = "숨겨진 메모도 스크롤해서 확인해보세요!",
                startPage = 61,
                endPage = 70
            ),
            CanvasMemoSummaryUiModel(
                memoId = "memo-6",
                title = "6번째 메모",
                startPage = 61,
                endPage = 70
            ),
            CanvasMemoSummaryUiModel(
                memoId = "memo-7",
                title = "7번째 메모",
                startPage = 61,
                endPage = 70
            )

        ),
        onItemClick = {}
    )
}