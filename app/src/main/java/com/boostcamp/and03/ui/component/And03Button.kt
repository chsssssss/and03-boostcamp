package com.boostcamp.and03.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.ui.theme.And03Theme

enum class ButtonVariant {
    Primary,
    Secondary,
    SurfaceVariant,
    Transparent
}

@Composable
fun And03Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: ButtonVariant = ButtonVariant.Primary,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding
) {
    val colors = when (variant) {
        ButtonVariant.Primary -> ButtonDefaults.buttonColors(
            containerColor = And03Theme.colors.primary,
            contentColor = And03Theme.colors.onPrimary
        )

        ButtonVariant.Secondary -> ButtonDefaults.buttonColors(
            containerColor = And03Theme.colors.surface,
            contentColor = And03Theme.colors.onSurface
        )

        ButtonVariant.SurfaceVariant -> ButtonDefaults.buttonColors(
            containerColor = And03Theme.colors.surfaceVariant,
            contentColor = And03Theme.colors.onSurface
        )

        ButtonVariant.Transparent -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = And03Theme.colors.onSurface
        )
    }

    Button(
        onClick = onClick,
        colors = colors,
        shape = And03Theme.shapes.defaultCorner,
        contentPadding = contentPadding,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = And03Theme.typography.labelLarge
        )
    }
}

@Preview
@Composable
private fun And03ButtonPrimaryPreview() {
    And03Theme {
        And03Button(
            text = "버튼",
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun And03ButtonSurfaceVariantPreview() {
    And03Theme {
        And03Button(
            text = "버튼",
            onClick = {},
            variant = ButtonVariant.SurfaceVariant
        )
    }
}

@Preview
@Composable
private fun And03ButtonSecondaryPreview() {
    And03Theme {
        And03Button(
            text = "버튼",
            onClick = {},
            variant = ButtonVariant.Secondary
        )
    }
}

@Preview
@Composable
private fun And03ButtonTransparentPreview() {
    And03Theme {
        And03Button(
            text = "버튼",
            onClick = {},
            variant = ButtonVariant.Transparent
        )
    }
}