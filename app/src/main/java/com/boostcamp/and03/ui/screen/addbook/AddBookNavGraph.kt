package com.boostcamp.and03.ui.screen.addbook

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.addBookNavGraph(
    modifier: Modifier = Modifier
) {
    composable<Route.AddBook> {
        AddBookScreen()
    }
}