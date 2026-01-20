package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.ui.screen.canvasmemo.component.AlertAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.AlertMessageCard
import com.boostcamp.and03.ui.screen.canvasmemo.component.RelationEditorDialog
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

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(text = "CanvasMemoScreen")

            AlertMessageCard(
                message = "삭제할 아이템을 선택해주세요.",
                modifier = Modifier.padding(And03Padding.PADDING_M),
                actions = listOf(
                    AlertAction("취소") { },
                    AlertAction("삭제") { }
                )
            )

            if (uiState.isRelationDialogVisible) {
                RelationEditorDialog(
                    relationNameState = uiState.relationNameState,
                    fromName = uiState.relationSelection
                        ?.fromNodeId
                        ?.let { uiState.nodes[it]?.node?.title } ?: "",
                    toName = uiState.relationSelection
                        ?.toNodeId
                        ?.let { uiState.nodes[it]?.node?.title } ?: "",
                    onDismiss = { onAction(CanvasMemoAction.CloseRelationDialog) },
                    onConfirm = { /* 관계 저장 */ },
                    onFromImageClick = { /* 왼쪽 인물 선택 */ },
                    onToImageClick = { /* 오른쪽 인물 선택 */ }
                )
            }
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