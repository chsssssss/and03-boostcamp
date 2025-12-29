package com.boostcamp.and03.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.ui.theme.MainTheme

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MainTheme.colors.primary,
            contentColor = MainTheme.colors.onPrimary
        ),
        shape = MainTheme.shapes.defaultCorner,
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MainTheme.typography.labelLarge,
            color = MainTheme.colors.onPrimary
        )
    }
}

@Preview
@Composable
private fun MainButtonPreview() {
    MainTheme {
        MainButton(
            text = "버튼",
            onClick = {}
        )
    }
}