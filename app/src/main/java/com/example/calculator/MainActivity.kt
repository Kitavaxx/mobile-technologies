package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculator.pages.AboutPage
import com.example.calculator.pages.AdvancedCalculator
import com.example.calculator.pages.BasicCalculator
import com.example.calculator.pages.CalculatorMenu
import kotlinx.serialization.Serializable

@Serializable
object Menu
@Serializable
object Basic
@Serializable
object Advanced
@Serializable
object About

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = Menu,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable<Menu> {
                            CalculatorMenu(navController)
                        }
                        composable<Basic> {
                            BasicCalculator()
                        }
                        composable<Advanced> {
                            AdvancedCalculator()
                        }
                        composable<About> {
                            AboutPage()
                        }
                    }
                }
            )
        }
    }
}

