package com.boostcamp.and03.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Booklist : Route

    @Serializable
    data object BookSearch : Route

    @Serializable
    data object AddBook : Route

    @Serializable
    data object MyPage : Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Booklist : MainTabRoute

    @Serializable
    data object AddBook : MainTabRoute

    @Serializable
    data object MyPage : MainTabRoute
}