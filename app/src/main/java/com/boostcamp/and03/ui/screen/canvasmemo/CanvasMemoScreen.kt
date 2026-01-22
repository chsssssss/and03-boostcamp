package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolExpandableButton
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBar
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarItem
import com.boostcamp.and03.ui.screen.canvasmemo.component.bottombar.MainBottomBarType
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.CanvasMemoColors

@Composable
fun CanvasMemoRoute(
    navigateToBack: () -> Unit,
    viewModel: CanvasMemoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is CanvasMemoEvent.NavToBack -> navigateToBack()
            }
        }
    }

    CanvasMemoScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun CanvasMemoScreen(
    uiState: CanvasMemoUiState,
    onAction: (CanvasMemoAction) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        ToolExpandableButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = And03Padding.PADDING_XS,
                    bottom = And03Padding.PADDING_4XL + And03ComponentSize.BOTTOM_BAR_ITEM_SIZE + And03Spacing.SPACE_L
                ),
            actions = listOf(
                ToolAction(
                    iconRes = R.drawable.ic_add_filled,
                    contentDescription = stringResource(R.string.tool_ic_content_desc_zoom_in),
                    onClick = { }
                ),
                ToolAction(
                    iconRes = R.drawable.ic_remove_filled,
                    contentDescription = stringResource(R.string.tool_ic_content_desc_zoom_out),
                    onClick = { }
                ),
                ToolAction(
                    iconRes = R.drawable.ic_fit_screen,
                    contentDescription = stringResource(R.string.tool_ic_content_desc_fit_screen),
                    onClick = { }
                )
            )
        )

        MainBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = And03Spacing.SPACE_XS),
            items = listOf(
                MainBottomBarItem(
                    type = MainBottomBarType.NODE,
                    label = "노드",
                    icon = Icons.Default.PersonAdd,
                    backgroundColor = CanvasMemoColors.Node
                ),
                MainBottomBarItem(
                    type = MainBottomBarType.RELATION,
                    label = "관계",
                    icon = Icons.Default.Link,
                    backgroundColor = CanvasMemoColors.Relation
                ),
                MainBottomBarItem(
                    type = MainBottomBarType.QUOTE,
                    label = "구절",
                    icon = Icons.Default.FormatQuote,
                    backgroundColor = CanvasMemoColors.Quote
                ),
                MainBottomBarItem(
                    type = MainBottomBarType.DELETE,
                    label = "삭제",
                    icon = Icons.Default.Delete,
                    backgroundColor = CanvasMemoColors.Delete
                )

            ),
            selectedType = uiState.selectedBottomBarType,
            onItemClick = { type ->
                onAction(CanvasMemoAction.OnBottomBarClick(type))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CanvasMemoScreenPreview() {
    val previewState = CanvasMemoUiState(
        selectedBottomBarType = MainBottomBarType.NODE
    )

    CanvasMemoScreen(
        uiState = previewState,
        onAction = {},
    )
}
