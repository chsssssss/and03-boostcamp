package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03Button
import com.boostcamp.and03.ui.component.And03InfoSection
import com.boostcamp.and03.ui.component.ButtonVariant
import com.boostcamp.and03.ui.component.CharacterCard
import com.boostcamp.and03.ui.component.SearchTextField
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.util.drawVerticalScrollbar


@Composable
fun AddNodeBottomSheet(
    characters: List<CharacterUiModel>,
    infoTitle: String,
    infoDescription: String,
    onSearch: (String) -> Unit,
    onNewCharacterClick: () -> Unit,
    onAddClick: (CharacterUiModel?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val searchState = rememberTextFieldState()
    val listState = rememberLazyListState()
    var selectedCharacterId by remember { mutableStateOf<String?>(null) }

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
            text = stringResource(R.string.add_node_bottom_sheet_title),
            style = And03Theme.typography.titleMedium,
            color = And03Theme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_L))

        And03InfoSection(
            title = infoTitle,
            description = infoDescription
        )

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_M))

        SearchTextField(
            state = searchState,
            onSearch = { onSearch(searchState.text.toString()) },
            modifier = Modifier.fillMaxWidth(),
            placeholderRes = R.string.add_node_bottom_sheet_search_placeholder
        )

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_M))

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .drawVerticalScrollbar(
                    state = listState,
                    color = And03Theme.colors.outlineVariant
                ),
            verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S),
            contentPadding = PaddingValues(bottom = And03Padding.PADDING_M)
        ) {
            items(
                items = characters,
                key = { it.id }
            ) { character ->
                CharacterCard(
                    name = character.name,
                    role = character.role,
                    iconColor = character.iconColor,
                    description = character.description,
                    selected = selectedCharacterId == character.id,
                    onClick = { selectedCharacterId = character.id },
                    onEditClick = {},
                    onDeleteClick = {}
                )
            }
        }

        TextButton(
            onClick = onNewCharacterClick,
            modifier = Modifier.padding(vertical = And03Spacing.SPACE_S)
        ) {
            Text(
                text = stringResource(R.string.add_node_bottom_sheet_new_character),
                style = And03Theme.typography.labelLarge,
                color = And03Theme.colors.primary
            )
        }

        And03Button(
            text = stringResource(R.string.add_node_bottom_sheet_add_button),
            onClick = {
                val selected = characters.find { it.id == selectedCharacterId }
                onAddClick(selected)
            },
            variant = ButtonVariant.Primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(And03ComponentSize.BUTTON_HEIGHT_L)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun AddNodeBottomSheetPreview() {
    And03Theme {
        AddNodeBottomSheet(
            characters = List(8) {
                CharacterUiModel(
                    id = it.toString(),
                    name = "헤르미온느 그레인저",
                    role = "조연",
                    description = "뛰어난 마법 실력을 가진 해리의 친구",
                    iconColor = Color(0xFF4CAF50)
                )
            },
            infoTitle = "등장인물을 선택해 노드를 추가해주세요.",
            infoDescription = "아직 등록하지 않았다면 새로 추가할 수 있어요.",
            onSearch = {},
            onNewCharacterClick = {},
            onAddClick = {}
        )
    }
}
