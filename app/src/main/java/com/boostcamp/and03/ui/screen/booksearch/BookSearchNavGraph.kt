package com.boostcamp.and03.ui.screen.booksearch

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.MainNavigator
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.bookSearchNavGraph(
    navigator: MainNavigator,
    modifier: Modifier = Modifier
) {
    composable<Route.BookSearch> {
        BookSearchRoute(
            onBackClick = navigator::navigatePopBackStack
        )
    }
}