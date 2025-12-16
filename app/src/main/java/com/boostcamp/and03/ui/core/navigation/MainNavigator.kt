package com.boostcamp.and03.ui.core.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import kotlinx.collections.immutable.toImmutableList

@Stable
class MainNavigator(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() =
            navController.currentBackStackEntryAsState().value?.destination

    val startDestination = MainTabRoute.AddBook

    val mainBottomTabs = MainBottomTab.entries.toImmutableList()

    val currentTab: MainBottomTab?
        @Composable get() {
            return MainBottomTab.entries.find { tab ->
                currentDestination?.hasRoute(tab.route::class) == true
            }
        }

    val isShowBottomBar: Boolean
        @Composable get() = MainBottomTab.entries.any { tab ->
            currentDestination?.hasRoute(tab.route::class) == true
        }

    fun navigate(tab: MainTabRoute) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTabRoute.Booklist -> navController.navigateBooklist(navOptions)
            MainTabRoute.AddBook -> navController.navigateAddBook(navOptions)
            MainTabRoute.MyPage -> navController.navigateMyPage(navOptions)
        }
    }

    fun NavController.navigateBooklist(navOptions: NavOptions) {
        navigate(MainTabRoute.Booklist, navOptions)
    }

    fun NavController.navigateAddBook(navOptions: NavOptions) {
        navigate(MainTabRoute.AddBook, navOptions)
    }

    fun NavController.navigateMyPage(navOptions: NavOptions) {
        navigate(MainTabRoute.MyPage, navOptions)
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