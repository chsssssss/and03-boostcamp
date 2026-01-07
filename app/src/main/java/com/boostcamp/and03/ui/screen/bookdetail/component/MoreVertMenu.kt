package com.boostcamp.and03.ui.screen.bookdetail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun MoreVertMenu(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_more_vert_filled),
                contentDescription = stringResource(R.string.cd_more_options)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = And03Theme.colors.surface
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.more_vert_edit)) },
                onClick = {
                    expanded = false
                    onEditClick()
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(R.string.more_vert_delete)) },
                onClick = {
                    expanded = false
                    onDeleteClick()
                }
            )
        }
    }
}