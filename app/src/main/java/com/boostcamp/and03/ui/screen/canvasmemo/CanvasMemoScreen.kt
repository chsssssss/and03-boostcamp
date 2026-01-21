package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.screen.canvasmemo.component.AddCharacterDialog
import com.boostcamp.and03.ui.screen.canvasmemo.component.AlertAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.AlertMessageCard
import com.boostcamp.and03.ui.screen.canvasmemo.component.RelationEditorDialog
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolExpandableButton
import com.boostcamp.and03.ui.theme.And03Padding

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
    Scaffold(
        topBar = {
            And03AppBar(
                title = "임시 제목",
                onBackClick = { onAction(CanvasMemoAction.ClickBack) }
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_more_vert_filled),
                        contentDescription = stringResource(
                            R.string.content_description_more_button
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "CanvasMemoScreen")

                AlertMessageCard(
                    message = "삭제할 아이템을 선택해주세요.",
                    modifier = Modifier.padding(And03Padding.PADDING_M),
                    actions = listOf(
                        AlertAction("취소") { },
                        AlertAction("삭제") { }
                    )
                )

//                if (uiState.isRelationDialogVisible) {
//                    RelationEditorDialog(
//                        relationNameState = uiState.relationNameState,
//                        fromName = uiState.relationSelection
//                            ?.fromNodeId
//                            ?.let { uiState.nodes[it]?.node?.na } ?: "",
//                        toName = uiState.relationSelection
//                            ?.toNodeId
//                            ?.let { uiState.nodes[it]?.node?.title } ?: "",
//                        onDismiss = { onAction(CanvasMemoAction.CloseRelationDialog) },
//                        onConfirm = { /* 관계 저장 */ },
//                        onFromImageClick = { /* 왼쪽 인물 선택 */ },
//                        onToImageClick = { /* 오른쪽 인물 선택 */ }
//                    )
//                }

                if (uiState.isAddCharacterDialogVisible) {
                    AddCharacterDialog(
                        nameState = uiState.characterNameState,
                        descState = uiState.characterDescState,
                        enabled = uiState.characterNameState.text.isNotBlank(),
                        onDismiss = { onAction(CanvasMemoAction.CloseAddCharacterDialog) },
                        onConfirm = { /* 캐릭터 저장 */ },
                        onClickAddImage = { /* 이미지 추가 */ }
                    )
                }
            }

            ToolExpandableButton(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = And03Padding.PADDING_L, bottom = And03Padding.PADDING_2XL),
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CanvasMemoScreenPreview() {
    val previewState = CanvasMemoUiState()

    CanvasMemoScreen(
        uiState = previewState,
        onAction = {},
    )

}