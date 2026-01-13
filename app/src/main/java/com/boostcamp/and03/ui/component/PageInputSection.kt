package com.boostcamp.and03.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun PageInputSection(
    startPage: String,
    endPage: String,
    onStartPageChange: (String) -> Unit,
    onEndPageChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S)
    ) {
        Text(
            text = stringResource(id = R.string.add_memo_page_information),
            style = MaterialTheme.typography.labelLarge,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = startPage,
                onValueChange = onStartPageChange,
                placeholder = { Text(text = stringResource(id = R.string.add_memo_enter_start_page_placeholder)) },
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(And03Radius.RADIUS_M)
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_remove_filled),
                contentDescription = null,
                tint = And03Theme.colors.outline,
                modifier = Modifier
                    .padding(horizontal = And03Spacing.SPACE_S)
                    .align(Alignment.CenterVertically)
            )

            OutlinedTextField(
                value = endPage,
                onValueChange = onEndPageChange,
                placeholder = { Text(text = stringResource(id = R.string.add_memo_enter_end_page_placeholder)) },
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(And03Radius.RADIUS_M)
            )
        }

        Text(
            text = stringResource(R.string.add_memo_page_notice),
            style = And03Theme.typography.labelMedium,
            color = And03Theme.colors.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PageInputSectionPreview() {
    And03Theme {
        PageInputSection(
            startPage = "1",
            endPage = "10",
            onStartPageChange = {},
            onEndPageChange = {}
        )
    }
}