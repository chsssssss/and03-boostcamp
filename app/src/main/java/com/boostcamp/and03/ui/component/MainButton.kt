package com.boostcamp.and03.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.ui.theme.MainTheme

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Primary,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding
) {
    val colors = when (variant) {
        ButtonVariant.Primary -> ButtonDefaults.buttonColors(
            containerColor = MainTheme.colors.primary,
            contentColor = MainTheme.colors.onPrimary
        )

        ButtonVariant.Secondary -> ButtonDefaults.buttonColors(
            containerColor = MainTheme.colors.surface,
            contentColor = MainTheme.colors.onSurface
        )

        ButtonVariant.Transparent -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MainTheme.colors.onSurface
        )
    }

    Button(
        onClick = onClick,
        colors = colors,
        shape = MainTheme.shapes.defaultCorner,
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MainTheme.typography.labelLarge
        )
    }
}

enum class ButtonVariant {
    Primary,
    Secondary,
    Transparent
}

@Preview
@Composable
private fun MainButtonPrimaryPreview() {
    MainTheme {
        MainButton(
            text = "버튼",
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun MainButtonSecondaryPreview() {
    MainTheme {
        MainButton(
            text = "버튼",
            onClick = {},
            variant = ButtonVariant.Secondary
        )
    }
}

@Preview
@Composable
private fun MainButtonTransparentPreview() {
    MainTheme {
        MainButton(
            text = "버튼",
            onClick = {},
            variant = ButtonVariant.Transparent
        )
    }
}