package com.boostcamp.and03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.boostcamp.and03.ui.navigation.rememberMainNavigator
import com.boostcamp.and03.ui.screen.prototype.navigation.PrototypeNavHost
import com.boostcamp.and03.ui.theme.And03Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            And03Theme {
//                val navigator = rememberMainNavigator()
//                MainApp(navigator)
                PrototypeNavHost()
            }
        }
    }
}