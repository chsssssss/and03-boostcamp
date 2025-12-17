package com.boostcamp.and03.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.boostcamp.and03.ui.screen.addbook.addBookNavGraph
import com.boostcamp.and03.ui.screen.booklist.booklistNavGraph
import com.boostcamp.and03.ui.screen.mypage.myPageNavGraph

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination
    ) {
        booklistNavGraph(modifier = modifier.padding(paddingValues))

        addBookNavGraph(modifier = modifier.padding(paddingValues))

        myPageNavGraph(modifier = modifier.padding(paddingValues))
    }
}