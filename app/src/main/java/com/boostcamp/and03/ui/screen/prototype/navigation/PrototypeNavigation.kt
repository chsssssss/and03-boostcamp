package com.boostcamp.and03.ui.screen.prototype.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boostcamp.and03.ui.screen.prototype.screen.CanvasScreen
import com.boostcamp.and03.ui.screen.prototype.screen.MemoEditScreen
import kotlinx.serialization.Serializable

@Composable
fun PrototypeNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = PrototypeRoute.Canvas
    ) {
        composable<PrototypeRoute.Canvas> {
            CanvasScreen(
                navController = navController,
                onEditMemoClick = {
                    navController.navigate(PrototypeRoute.MemoEdit)
                }
            )
        }

        composable<PrototypeRoute.MemoEdit> {
            MemoEditScreen(
                onClose = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed interface PrototypeRoute {

    @Serializable
    data object Canvas: PrototypeRoute

    @Serializable
    data object MemoEdit: PrototypeRoute
}