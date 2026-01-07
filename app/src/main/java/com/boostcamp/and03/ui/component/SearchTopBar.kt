package com.boostcamp.and03.ui.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03Theme

// 임시로 사용할 탑 바
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    title: String,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSaveEnabled: Boolean = false
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_keyboard_arrow_left_filled),
                    contentDescription = stringResource(R.string.content_description_go_back)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSaveClick,
                enabled = isSaveEnabled
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check_filled),
                    contentDescription = stringResource(R.string.content_description_save_button)
                )
            }
        }
    )
}

@Preview
@Composable
private fun SearchTopBarPreview() {
    And03Theme {
        SearchTopBar(
            title = "책 검색",
            onBackClick = {},
            onSaveClick = {}
        )
    }
}