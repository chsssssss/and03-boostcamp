package com.boostcamp.and03.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.MainTheme

@Composable
fun CircleIconItem(
    @DrawableRes iconResId: Int,
    iconColor: Color,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp
) {
    val isBright = iconColor.luminance() > 0.5f
    val backgroundColor = if (isBright) {
        lerp(iconColor, Color.Black, 0.3f)
    } else {
        lerp(iconColor, Color.White, 0.7f)
    }

    Box(
        modifier = modifier
            .size(size)
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconResId),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun CircleIconItemPreview() {
    CircleIconItem(
        iconResId = R.drawable.ic_person_filled,
        iconColor = MainTheme.colors.primary
    )
}