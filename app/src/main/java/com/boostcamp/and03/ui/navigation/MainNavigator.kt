package com.boostcamp.and03.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions

@Stable
class MainNavigator(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() =
            navController.currentBackStackEntryAsState().value?.destination

    val startDestination = Route.Booklist

    fun navigate(tab: Route) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            Route.Booklist -> navController.navigateBooklist(navOptions)
            Route.BookSearch -> navController.navigateBookSearch(navOptions)
            Route.AddBook -> navController.navigateAddBook(navOptions)
            Route.MyPage -> navController.navigateMyPage(navOptions)
            Route.BookDetail -> navController.navigateBookDetail(navOptions)
        }
    }

    fun NavController.navigateBooklist(navOptions: NavOptions) {
        navigate(Route.Booklist, navOptions)
    }

    fun NavController.navigateBookSearch(navOptions: NavOptions) {
        navigate(Route.BookSearch, navOptions)
    }

    fun NavController.navigateAddBook(navOptions: NavOptions) {
        navigate(Route.AddBook, navOptions)
    }

    fun NavController.navigateMyPage(navOptions: NavOptions) {
        navigate(Route.MyPage, navOptions)
    }

    fun NavController.navigateBookDetail(navOptions: NavOptions) {
        navigate(Route.BookDetail, navOptions)
    }

    fun navigatePopBackStack() = navController.popBackStack()
}

@SuppressLint("ComposableNaming")
@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}