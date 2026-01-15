package com.boostcamp.and03.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03ComponentSize.APP_BAR_HEIGHT
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun And03AppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Top
                )
            )
            .height(APP_BAR_HEIGHT)
            .padding(horizontal = And03Padding.PADDING_M),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = And03Theme.typography.titleMedium,
            color = And03Theme.colors.onSurface,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_left_back),
                        contentDescription = stringResource(
                            R.string.content_description_go_back
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            actions()
        }
    }
}

@Preview(
    name = "Back + Check",
    showBackground = true
)
@Composable
private fun And03AppBarPreview_BackCheckMenu() {
    And03Theme {
        And03AppBar(
            title = "편집",
            onBackClick = {}
        ) {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_check_filled),
                    contentDescription = stringResource(
                        R.string.content_description_confirm
                    )
                )
            }
        }
    }
}

@Preview(
    name = "Back + Menu",
    showBackground = true
)
@Composable
private fun And03AppBarPreview_BackMenu() {
    And03Theme {
        And03AppBar(
            title = "책 정보",
            onBackClick = {}
        ) {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_more_vert_filled),
                    contentDescription = stringResource(
                        R.string.content_description_more_button
                    )
                )
            }
        }
    }
}

@Preview(
    name = "Back only",
    showBackground = true
)
@Composable
private fun And03AppBarPreview_BackOnly() {
    And03Theme {
        And03AppBar(
            title = "책 정보",
            onBackClick = {}
        )
    }
}

@Preview(
    name = "Actions only",
    showBackground = true
)
@Composable
private fun And03AppBarPreview_ActionsOnly() {
    And03Theme {
        And03AppBar(
            title = "홈"
        ) {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_more_vert_filled),
                    contentDescription = stringResource(
                        R.string.content_description_more_button
                    )
                )
            }
        }
    }
}

@Preview(
    name = "Title only",
    showBackground = true
)
@Composable
private fun And03AppBarPreview_TitleOnly() {
    And03Theme {
        And03AppBar(
            title = "홈"
        )
    }
}
