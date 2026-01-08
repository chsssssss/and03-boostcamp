package com.boostcamp.and03.ui.screen.bookdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun SquareAddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .clip(RoundedCornerShape(And03Radius.RADIUS_M))
            .background(And03Theme.colors.primary),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_btn_cd),
            tint = And03Theme.colors.onSecondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SquareAddButtonPreview() {
    SquareAddButton(onClick = {})
}
