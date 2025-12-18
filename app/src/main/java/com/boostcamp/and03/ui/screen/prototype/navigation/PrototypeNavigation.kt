package com.boostcamp.and03.ui.screen.prototype.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boostcamp.and03.ui.screen.prototype.screen.CanvasScreen
import com.boostcamp.and03.ui.screen.prototype.screen.MemoEditScreen
import com.boostcamp.and03.ui.screen.prototype.viewmodel.CanvasViewModel
import kotlinx.serialization.Serializable

@Composable
fun PrototypeNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = PrototypeRoute.Canvas
    ) {
        composable<PrototypeRoute.Canvas> { backStackEntry ->
            val canvasViewModel: CanvasViewModel =
                viewModel(backStackEntry)

            CanvasScreen(
                navController = navController,
                onEditMemoClick = {
                    navController.navigate(PrototypeRoute.MemoEdit)
                },
                viewModel = canvasViewModel
            )
        }

        composable<PrototypeRoute.MemoEdit> {
            MemoEditScreen(
                onBack = {
                    navController.popBackStack()
                },
                onSave = { title, content ->
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