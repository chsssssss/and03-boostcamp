package com.boostcamp.and03.ui.screen.addquote

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.component.And03Button
import com.boostcamp.and03.ui.component.ButtonVariant
import com.boostcamp.and03.ui.theme.*
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03InfoSection
import com.boostcamp.and03.ui.screen.addquote.component.AddQuoteInputSection

@Composable
fun AddQuoteScreen(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Scaffold(
        topBar = {
            And03AppBar(
                title = stringResource(R.string.add_quote_title),
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            And03Button(
                text = stringResource(R.string.content_description_save_button),
                onClick = onSaveClick,
                variant = ButtonVariant.Primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(And03Padding.PADDING_L)
                    .height(And03ComponentSize.BUTTON_HEIGHT_L)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = And03Padding.PADDING_XL)
        ) {
            Spacer(modifier = Modifier.height(And03Spacing.SPACE_L))

            And03InfoSection()

            Spacer(modifier = Modifier.height(And03Spacing.SPACE_2XL))

            AddQuoteInputSection()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddQuoteScreenPreview() {
    And03Theme {
        AddQuoteScreen({}, {})
    }
}
