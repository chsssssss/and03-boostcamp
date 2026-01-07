package com.boostcamp.and03.ui.screen.booklist

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.MainTabRoute

fun NavGraphBuilder.booklistNavGraph(
    onShowSnackBar: (message: String, actionLabel: String) -> Unit,
    navigateToBookDetail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<MainTabRoute.Booklist> {
        BooklistRoute(
            onBookClick = {
                navigateToBookDetail()
            }
        )
    }
}