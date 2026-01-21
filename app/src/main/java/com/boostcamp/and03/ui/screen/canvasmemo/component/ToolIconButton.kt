package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Elevation
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun ToolIconButton(
    @DrawableRes iconRes: Int,
    contentDescription: String?,
    onClick: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(And03Radius.RADIUS_L),
        color = And03Theme.colors.surface,
        shadowElevation = And03Elevation.M,
        modifier = Modifier
            .size(And03ComponentSize.BUTTON_SIZE)
            .clickable { onClick() }

    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = contentDescription
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToolIconButtonPreview() {
    ToolIconButton(
        iconRes = R.drawable.ic_round_build,
        contentDescription = null,
        onClick = {}
    )
}