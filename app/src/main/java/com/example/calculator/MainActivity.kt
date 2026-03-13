package com.example.calculator

import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                            BasicCalculatorUI(navController)
                        }
                        composable<Advanced> {
                            AdvancedCalculatorUI(navController)
                        }
                        composable<About> {
                            AboutPageUI(navController)
                        }
                    }
                }
            )
        }
    }
}
@Composable
fun CalculatorMenu(
    navController: NavController,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Calculator Menu",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        MenuButton("Basic"){
            navController.navigate(Basic)
        }
        MenuButton("Advanced"){
            navController.navigate(Advanced)
        }
        MenuButton("About"){
            navController.navigate(About)
        }
        MenuButton("Exit"){
            navController.popBackStack()
        }
    }
}


@Composable
fun MenuButton(text: String = "", onClick: () -> Unit = {}){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
    ){
        Text(
            text = text,
            fontSize = 20.sp
        )
    }
}

@Composable
fun BasicCalculatorUI(navController : NavController) {
    MenuButton("Back"){
        navController.popBackStack()
    }
}

@Composable
fun AdvancedCalculatorUI(navController : NavController) {
    MenuButton("Back"){
        navController.popBackStack()
    }
}
@Composable
fun AboutPageUI(navController : NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = "Calculator App\n\n",
            fontSize = 34.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Text(
            text =  "Made by: Dmytro Malinovskyi - 252501\n\n\n" +
                    "This calculator app combines both basic and advanced functionality in one place " +
                    "It handles standard arithmetic operations like addition, subtraction, multiplication, and " +
                    "division as well as scientific functions such as square roots," +
                    " exponents, trigonometry, and logarithms—perfect for everyday calculations and " +
                    "more complex math needs.",
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.size(60.dp))
        MenuButton("Back"){
            navController.popBackStack()
        }
    }
}

@Composable
fun CalculatorButton(label: String, onClick: (String) -> Unit) {

}
