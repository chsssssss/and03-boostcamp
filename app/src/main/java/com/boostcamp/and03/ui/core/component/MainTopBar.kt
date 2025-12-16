package com.boostcamp.and03.ui.core.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.boostcamp.and03.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    titleText: String,
    onBackCLick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = titleText) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackCLick) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_filled),
                    contentDescription = stringResource(R.string.content_description_go_back)
                )
            }
        }
    )
}