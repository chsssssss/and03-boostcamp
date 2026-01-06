package com.boostcamp.and03.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun ActionSnackBarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
    ) { snackbarData ->
        Snackbar(
            snackbarData = snackbarData,
            shape = RoundedCornerShape(12.dp),
            actionColor = And03Theme.colors.secondary,
            actionOnNewLine = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActionSnackBarHostPreview() {
    ActionSnackBarHost(hostState = SnackbarHostState())
}